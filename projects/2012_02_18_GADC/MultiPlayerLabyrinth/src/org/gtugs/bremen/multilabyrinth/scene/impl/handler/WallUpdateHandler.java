package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

public class WallUpdateHandler implements IUpdateHandler{

	// TODO: needed?
	// private float secondsElapsed = 0; 
	
	private final Sprite ball;
	
	private final Rectangle wall;

	private final Sound hitWallSound;
	
	public WallUpdateHandler(final Sprite ball, final Rectangle wall, final Sound hitWallSound){
		super();
		this.ball = ball;
		this.wall = wall;
		this.hitWallSound = hitWallSound;
	}
	
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		if(ball.collidesWith(wall)) {
			hitWallSound.play();
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		// Nothing to do ...
	}

}