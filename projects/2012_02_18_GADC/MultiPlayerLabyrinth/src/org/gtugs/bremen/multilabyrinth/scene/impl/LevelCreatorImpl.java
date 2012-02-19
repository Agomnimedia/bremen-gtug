package org.gtugs.bremen.multilabyrinth.scene.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.Entity;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.ExpireParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunication;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.api.RemovePhysicsHandler;
import org.gtugs.bremen.multilabyrinth.scene.api.Theme;
import org.gtugs.bremen.multilabyrinth.scene.elements.Element;
import org.gtugs.bremen.multilabyrinth.scene.impl.handler.EndPointUpdateHandler;
import org.gtugs.bremen.multilabyrinth.scene.impl.handler.PortalUpdateHandler;
import org.gtugs.bremen.multilabyrinth.scene.impl.handler.TrapUpdateHandler;
import org.gtugs.bremen.multilabyrinth.scene.impl.handler.WallUpdateHandler;

import android.opengl.GLES20;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class LevelCreatorImpl implements LevelCreator, RemovePhysicsHandler {

	private final VertexBufferObjectManager vertexBufferObjectManager;
	
	private final PhysicsWorld physicsWorld;
	
	private final ITextureRegion ballRegion;
	
	private final ITextureRegion trapRegion;
	
	private final ITextureRegion startRegion;
	
	private final ITextureRegion endRegion;
	
	private final ITextureRegion particleRegion;
	
	private List<Line> portals = null;
	
	private List<Sprite> traps = null;
	
	private List<Sprite> endPoints = null;

	private List<Rectangle> walls = null;
	
	private Hashtable<Entity, PhysicsConnector> connectorHashtable = new Hashtable<Entity, PhysicsConnector>();

	private Sound hitWallSound;
	
	private Sound trapSound;

	private Sound finishBallSound;
	
	private NetworkCommunication networkCommunication;
	
	public LevelCreatorImpl(final VertexBufferObjectManager vertexBufferObjectManager, 
			final Theme theme, final NetworkCommunication networkCommunication){
		this.networkCommunication = networkCommunication;
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.physicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
		this.ballRegion = theme.getBallRegion();
		this.trapRegion = theme.getTrapRegion();
		this.startRegion = theme.getStartRegion();
		this.endRegion = theme.getEndRegion();
		this.particleRegion = theme.getParticleRegion();
		this.hitWallSound = theme.getHitWallSound();
		this.trapSound = theme.getTrapSound();
		this.finishBallSound = theme.getFinishBallSound();
	}
	
	@Override
	public Scene createScene(final LevelInformation levelInfo) {
		final Scene scene = new Scene();
		scene.setBackground(new Background(new Color(0.6f, 0.45f, 0.09f, 1f)));
		final List<SceneElement> sceneElements = new ArrayList<SceneElement>(levelInfo.getElementList().size());
		for(final Element element : levelInfo.getElementList()){
			switch(element.getElementKind()){
			case WALL:
				{
					final float[] positions = element.getPositions();
					sceneElements.add(new SceneElement(this.createWall(scene, positions[0], positions[1], positions[2], positions[3]), element.getElementKind()));
				break;
				}
			case BALL:
				{
					final float[] positions = element.getPositions();
					sceneElements.add(new SceneElement(this.createBall(scene, positions[0], positions[1]), element.getElementKind()));
				}
				break;
			case TRAP:
				{
					final float[] positions = element.getPositions();
					sceneElements.add(new SceneElement(this.createTrap(scene, positions[0], positions[1]), element.getElementKind()));
				}
				break;
			case START_POINT:
				{
					final float[] positions = element.getPositions();
					sceneElements.add(new SceneElement(this.createStart(scene, positions[0], positions[1]), element.getElementKind()));
				}
				break;
			case END_POINT:
				{
					final float[] positions = element.getPositions();
					sceneElements.add(new SceneElement(this.createEnd(scene, positions[0], positions[1]), element.getElementKind()));
				}
				break;
			case PORTAL:
					final float[] positions = element.getPositions();
					sceneElements.add(new SceneElement(this.createPortal(scene, positions[0], positions[1], positions[2], positions[3]), element.getElementKind()));
				break;
			default:
					// nothing to implement
			}
		}
		
		scene.registerUpdateHandler(this.physicsWorld);
		this.registerAllHandlers(scene, sceneElements);
		return scene;
	}
	
	private void registerAllHandlers(final Scene scene, final List<SceneElement> sceneElements) {
		portals = new ArrayList<Line>();
		traps = new ArrayList<Sprite>();
		endPoints = new ArrayList<Sprite>();
		walls = new ArrayList<Rectangle>();
		final List<Sprite> balls = new ArrayList<Sprite>();
		
		for(final SceneElement se : sceneElements){
			switch(se.getKind()){
			case PORTAL:
				portals.add((Line)se.getShape());
				break;
			case TRAP:
				traps.add((Sprite) se.getShape());
				break;
			case END_POINT:
				endPoints.add((Sprite) se.getShape());
				break;
			case BALL:
				balls.add((Sprite) se.getShape());
				break;
			case WALL:
				walls.add((Rectangle) se.getShape());
				break;
			default:
					// do nothin
			}
		}
		
		for(final Sprite ball : balls){
			registerBallHandlers(scene, ball);
		}
	}

	private Line createPortal(final Scene scene, final float pX1, final float pY1, final float pX2, final float pY2) {
		final float lineWidth = 3.0f;
		final Line portal = new Line(pX1, pY1, pX2, pY2, lineWidth, this.vertexBufferObjectManager);
		portal.setColor(Color.BLACK);
		
		scene.attachChild(portal);
		
		// von pX1, pY1
		final float expireTime = 4f;
		{
			final SpriteParticleSystem particleSystem = new SpriteParticleSystem(new PointParticleEmitter(pX1-16, pY1-16), 
								8, 12, 200, this.particleRegion, this.vertexBufferObjectManager);
			particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
			particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>((pX2-pX1)*0.25f, (pY2-pY1)*0.25f));
			particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
			particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 0.0f, 0.0f));

			particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, expireTime, 0.5f, 1.0f));
			particleSystem.addParticleModifier(new ExpireParticleModifier<Sprite>(expireTime));

			scene.attachChild(particleSystem);
		}
		
		// von pX2, pY2
		{
			final SpriteParticleSystem particleSystem = new SpriteParticleSystem(new PointParticleEmitter(pX2-16, pY2-16), 
					8, 12, 200, this.particleRegion, this.vertexBufferObjectManager);
			particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
			particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>((pX1-pX2)*0.25f, (pY1-pY2)*0.25f));
			particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
			particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 0.0f, 0.0f));

			particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, expireTime, 0.5f, 1.0f));
			particleSystem.addParticleModifier(new ExpireParticleModifier<Sprite>(expireTime));

			scene.attachChild(particleSystem);
		}
		
		return portal;
	}

	private Sprite createEnd(final Scene scene, final float pX, final float pY) {
		final Sprite end = new Sprite(pX, pY,this.endRegion, this.vertexBufferObjectManager);
		scene.attachChild(end);
		return end;
	}

	private Sprite createStart(final Scene scene, final float pX, final float pY) {
		final Sprite start = new Sprite(pX, pY,this.startRegion, this.vertexBufferObjectManager);
		scene.attachChild(start);
		return start;
	}

	private Sprite createTrap(final Scene scene, final float pX, final float pY) {
		final Sprite trap = new Sprite(pX, pY,this.trapRegion, this.vertexBufferObjectManager);
		scene.attachChild(trap);
		return trap;
	}

	private Rectangle createWall(final Scene scene, final float pX, final float pY, final float width, final float height){
//		final float lineWidth = 20.0f;
//		final Line wall = new Line(pX1, pY1, pX2, pY2, lineWidth, this.vertexBufferObjectManager);
		final Rectangle wall = new Rectangle(pX, pY, width, height, this.vertexBufferObjectManager);
		
//		PhysicsFactory.createLineBody(this.physicsWorld, wall, PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f));
		PhysicsFactory.createBoxBody(this.physicsWorld, wall,BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0.1f, 0.05f));
		wall.setColor(Color.WHITE);
		
		scene.attachChild(wall);
		return wall;
	}
	
	private Sprite createBall(final Scene scene, final float pX, final float pY) {
		
		final Sprite ball;
		final Body body;
		
		ball = new Sprite(pX, pY, this.ballRegion, this.vertexBufferObjectManager);
		body = PhysicsFactory.createCircleBody(this.physicsWorld, ball, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(1f, 0.0f, 0.5f));
		body.setLinearDamping(1f);

		body.setFixedRotation(true);
		
		PhysicsConnector physicsConnector = new PhysicsConnector(ball, body, true, true);
		this.physicsWorld.registerPhysicsConnector(physicsConnector);
		scene.attachChild(ball);
		connectorHashtable.put(ball, physicsConnector);
		return ball;
	}

	@Override
	public void addBallToScene(Scene scene, float pX, float pY) {
		final Sprite ball = this.createBall(scene, pX, pY);
		
		registerBallHandlers(scene, ball);
	}
	
	private void registerBallHandlers(final Scene scene, final Sprite ball) {
		
		if(portals != null) {
			for(final Line portal : portals){
				scene.registerUpdateHandler(new PortalUpdateHandler(ball, portal, this.networkCommunication));
			}
		}
		if(endPoints != null) {
			for(final Sprite endPoint : endPoints){
				scene.registerUpdateHandler(new EndPointUpdateHandler(ball, endPoint, this.finishBallSound, this.networkCommunication, this));
			}
		}
		if(traps != null) {
			for(final Sprite trap : traps){
				scene.registerUpdateHandler(new TrapUpdateHandler(ball, trap, this.trapSound, this));
			}
		}
		if(walls != null) {
			for(final Rectangle wall : walls){
				scene.registerUpdateHandler(new WallUpdateHandler(ball, wall, this.hitWallSound));
			}
		}
	}

	@Override
	public void setAccelerationData(final AccelerationData pAccelerationData) {
		final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX()*10, pAccelerationData.getY()*10);
		this.physicsWorld.setGravity(gravity);
		Vector2Pool.recycle(gravity);
	}

	@Override
	public void removePhysics(Entity entity) {
		PhysicsConnector connector = connectorHashtable.get(entity);
		if(connector != null) {
			this.physicsWorld.unregisterPhysicsConnector(connector);
			connectorHashtable.remove(connectorHashtable);
		}
	}
	
	
}