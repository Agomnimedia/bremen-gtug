package org.gtugs.bremen.multilabyrinth.scene.impl;

import java.util.ArrayList;
import java.util.List;

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
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.api.Theme;
import org.gtugs.bremen.multilabyrinth.scene.elements.Element;
import org.gtugs.bremen.multilabyrinth.scene.impl.handler.EndPointUpdateHandler;
import org.gtugs.bremen.multilabyrinth.scene.impl.handler.PortalUpdateHandler;
import org.gtugs.bremen.multilabyrinth.scene.impl.handler.TrapUpdateHandler;

import android.hardware.SensorManager;
import android.opengl.GLES20;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class LevelCreatorImpl implements LevelCreator{

	private final VertexBufferObjectManager vertexBufferObjectManager;
	
	private final PhysicsWorld physicsWorld;
	
	private final ITextureRegion ballRegion;
	
	private final ITextureRegion trapRegion;
	
	private final ITextureRegion startRegion;
	
	private final ITextureRegion endRegion;
	
	private final ITextureRegion particleRegion;
	
	public LevelCreatorImpl(final VertexBufferObjectManager vertexBufferObjectManager, 
			final Theme theme){
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
		this.ballRegion = theme.getBallRegion();
		this.trapRegion = theme.getTrapRegion();
		this.startRegion = theme.getStartRegion();
		this.endRegion = theme.getEndRegion();
		this.particleRegion = theme.getParticleRegion();
	}
	
	@Override
	public Scene createScene(final LevelInformation levelInfo) {
		final Scene scene = new Scene();
		scene.setBackground(new Background(new Color(0, 200, 200)));
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
		final List<Line> portals = new ArrayList<Line>();
		final List<Sprite> traps = new ArrayList<Sprite>();
		final List<Sprite> endPoints = new ArrayList<Sprite>();
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
			default:
					// do nothin
			}
		}
		
		for(final Sprite ball : balls){
			for(final Line portal : portals){
				scene.registerUpdateHandler(new PortalUpdateHandler(ball, portal));
			}
			for(final Sprite endPoint : endPoints){
				scene.registerUpdateHandler(new EndPointUpdateHandler(ball, endPoint));
			}
			for(final Sprite trap : traps){
				scene.registerUpdateHandler(new TrapUpdateHandler(ball, trap));
			}
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
		PhysicsFactory.createBoxBody(this.physicsWorld, wall,BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f));
		wall.setColor(Color.WHITE);
		
		scene.attachChild(wall);
		return wall;
	}
	
	private Sprite createBall(final Scene scene, final float pX, final float pY) {
		
		final Sprite ball;
		final Body body;
		
		
		ball = new Sprite(pX, pY, this.ballRegion, this.vertexBufferObjectManager);
		body = PhysicsFactory.createCircleBody(this.physicsWorld, ball, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(3f, 0.5f, 3f));
		body.setFixedRotation(true);
		
		
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, body, true, true));
		scene.attachChild(ball);
		return ball;
	}

	@Override
	public void addBallToScene(Scene scene, float pX, float pY) {
		this.createBall(scene, pX, pY);
	}

	@Override
	public void setGravity(final Vector2 gravity) {
		this.physicsWorld.setGravity(gravity);
	}
	
	
}