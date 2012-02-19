package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;

public class TrapUpdateHandler implements IUpdateHandler {

	private final Sprite ball;
	
	private final Sprite trap;
	
	private final Sound hitWallSound;
	
	public TrapUpdateHandler(final Sprite ball, final Sprite trap, final Sound hitWallSound){
		this.ball = ball;
		this.trap = trap;
		this.hitWallSound = hitWallSound;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(ball.collidesWith(trap)) {
			// TODO Sound
			if(ball.contains(trap.getX(), trap.getY())) {
				hitWallSound.play();
				// TODO: Verloren!!!!!
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
