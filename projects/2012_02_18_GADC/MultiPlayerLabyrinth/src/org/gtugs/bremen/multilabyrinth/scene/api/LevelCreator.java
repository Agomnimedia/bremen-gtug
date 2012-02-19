package org.gtugs.bremen.multilabyrinth.scene.api;

import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.AccelerationData;

import com.badlogic.gdx.math.Vector2;

public interface LevelCreator {
	
	public Scene createScene(LevelInformation levelInfo);
	
	public void addBallToScene(final Scene scene, final float pX, final float pY);
	
	public void setAccelerationData(final AccelerationData pAccelerationData);
}
