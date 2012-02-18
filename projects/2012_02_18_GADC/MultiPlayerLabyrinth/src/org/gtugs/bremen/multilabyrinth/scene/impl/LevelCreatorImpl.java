package org.gtugs.bremen.multilabyrinth.scene.impl;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.elements.Element;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;

public class LevelCreatorImpl implements LevelCreator{

	private final VertexBufferObjectManager vertexBufferObjectManager;
	
	private final PhysicsWorld physicsWorld;
	
	
	public LevelCreatorImpl(final VertexBufferObjectManager vertexBufferObjectManager){
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), true);
	}
	
	@Override
	public Scene createScene(final LevelInformation levelInfo) {
		final Scene scene = new Scene();
		scene.setBackground(new Background(Color.PINK));
		for(final Element element : levelInfo.getElementList()){
			switch(element.getElementKind()){
			case WALL:
				final float[] positions = element.getPositions();
				this.createWall(scene, positions[0], positions[1], positions[2], positions[3]);
				break;
			default:
					// TODO implement other kinds
			}
		}
		
		scene.registerUpdateHandler(this.physicsWorld);
		return scene;
	}
	
	private void createWall(final Scene scene, final float pX1, final float pY1, final float pX2, final float pY2){
		final float lineWidth = 20.0f;
		final Line wall = new Line(pX1, pY1, pX2, pY2, lineWidth, this.vertexBufferObjectManager);
		
		PhysicsFactory.createLineBody(this.physicsWorld, wall, PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f));
		
		scene.attachChild(wall);
	}
}