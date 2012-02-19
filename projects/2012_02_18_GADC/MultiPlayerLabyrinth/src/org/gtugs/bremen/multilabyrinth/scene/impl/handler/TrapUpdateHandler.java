package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;

public class TrapUpdateHandler implements IUpdateHandler {

	private final Sprite ball;
	
	private final Sprite trap;
	
	public TrapUpdateHandler(final Sprite ball, final Sprite trap){
		this.ball = ball;
		this.trap = trap;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
