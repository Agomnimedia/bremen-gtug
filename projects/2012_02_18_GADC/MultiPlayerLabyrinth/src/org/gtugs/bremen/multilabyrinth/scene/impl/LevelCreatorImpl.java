package org.gtugs.bremen.multilabyrinth.scene.impl;

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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.elements.Element;

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
			final ITextureRegion ballRegion, final ITextureRegion trapRegion, final ITextureRegion startRegion,
			final ITextureRegion endRegion, final ITextureRegion particleRegion){
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
		this.ballRegion = ballRegion;
		this.trapRegion = trapRegion;
		this.startRegion = startRegion;
		this.endRegion = endRegion;
		this.particleRegion = particleRegion;
	}
	
	@Override
	public Scene createScene(final LevelInformation levelInfo) {
		final Scene scene = new Scene();
		scene.setBackground(new Background(new Color(0, 200, 200)));
		for(final Element element : levelInfo.getElementList()){
			switch(element.getElementKind()){
			case WALL:
				{
					final float[] positions = element.getPositions();
					this.createWall(scene, positions[0], positions[1], positions[2], positions[3]);
				break;
				}
			case BALL:
				{
					final float[] positions = element.getPositions();
					this.createBall(scene, positions[0], positions[1]);
				}
				break;
			case TRAP:
				{
					final float[] positions = element.getPositions();
					this.createTrap(scene, positions[0], positions[1]);
				}
				break;
			case START_POINT:
				{
					final float[] positions = element.getPositions();
					this.createStart(scene, positions[0], positions[1]);
				}
				break;
			case END_POINT:
				{
					final float[] positions = element.getPositions();
					this.createEnd(scene, positions[0], positions[1]);
				}
				break;
			case PORTAL:
					final float[] positions = element.getPositions();
					this.createPortal(scene, positions[0], positions[1], positions[2], positions[3]);
				break;
			default:
					// nothing to implement
			}
		}
		
		scene.registerUpdateHandler(this.physicsWorld);
		return scene;
	}
	
	private void createPortal(final Scene scene, final float pX1, final float pY1, final float pX2, final float pY2) {
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
	}

	private void createEnd(final Scene scene, final float pX, final float pY) {
		final Sprite end = new Sprite(pX, pY,this.endRegion, this.vertexBufferObjectManager);
		scene.attachChild(end);
	}

	private void createStart(final Scene scene, final float pX, final float pY) {
		final Sprite start = new Sprite(pX, pY,this.startRegion, this.vertexBufferObjectManager);
		scene.attachChild(start);
	}

	private void createTrap(final Scene scene, final float pX, final float pY) {
		final Sprite trap = new Sprite(pX, pY,this.trapRegion, this.vertexBufferObjectManager);
		scene.attachChild(trap);
	}

	private void createWall(final Scene scene, final float pX, final float pY, final float width, final float height){
//		final float lineWidth = 20.0f;
//		final Line wall = new Line(pX1, pY1, pX2, pY2, lineWidth, this.vertexBufferObjectManager);
		final Rectangle wall = new Rectangle(pX, pY, width, height, this.vertexBufferObjectManager);
		
//		PhysicsFactory.createLineBody(this.physicsWorld, wall, PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f));
		PhysicsFactory.createBoxBody(this.physicsWorld, wall,BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f));
		wall.setColor(Color.WHITE);
		
		scene.attachChild(wall);
	}
	
	private void createBall(final Scene scene, final float pX, final float pY) {
		
		final Sprite ball;
		final Body body;
		
		ball = new Sprite(pX, pY, this.ballRegion, this.vertexBufferObjectManager);
		body = PhysicsFactory.createCircleBody(this.physicsWorld, ball, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f));
		
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, body, true, true));
		scene.attachChild(ball);
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