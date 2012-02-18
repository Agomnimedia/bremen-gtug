package org.gtugs.bremen.multilabyrinth.scene.impl;

import org.andengine.entity.primitive.Line;
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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class LevelCreatorImpl implements LevelCreator{

	private final VertexBufferObjectManager vertexBufferObjectManager;
	
	private final PhysicsWorld physicsWorld;
	
	private final ITextureRegion ballRegion;
	
	private final ITextureRegion trapRegion;
	
	public LevelCreatorImpl(final VertexBufferObjectManager vertexBufferObjectManager, final ITextureRegion ballRegion, final ITextureRegion trapRegion){
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), true);
		this.ballRegion = ballRegion;
		this.trapRegion = trapRegion;
	}
	
	@Override
	public Scene createScene(final LevelInformation levelInfo) {
		final Scene scene = new Scene();
		scene.setBackground(new Background(Color.PINK));
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
			default:
					// TODO implement other kinds
			}
		}
		
		scene.registerUpdateHandler(this.physicsWorld);
		return scene;
	}
	
	private void createTrap(final Scene scene, final float pX, final float pY) {
		final Sprite trap = new Sprite(pX, pY,this.trapRegion, this.vertexBufferObjectManager);
		scene.attachChild(trap);
	}

	private void createWall(final Scene scene, final float pX1, final float pY1, final float pX2, final float pY2){
		final float lineWidth = 20.0f;
		final Line wall = new Line(pX1, pY1, pX2, pY2, lineWidth, this.vertexBufferObjectManager);
		
		PhysicsFactory.createLineBody(this.physicsWorld, wall, PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f));
		
		scene.attachChild(wall);
	}
	
	private void createBall(final Scene scene, final float pX, final float pY) {
		
//		final CircleShape ball = new Cir
//		PhysicsFactory.createCircleBody(this.physicsWorld, ballTextureRegion, PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f));
		
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