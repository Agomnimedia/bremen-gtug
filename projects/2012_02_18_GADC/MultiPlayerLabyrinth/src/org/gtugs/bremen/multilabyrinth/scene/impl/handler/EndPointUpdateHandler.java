package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;

public class EndPointUpdateHandler implements IUpdateHandler {

	private final Sprite ball;
	
	private final Sprite endPoint;
	
	public EndPointUpdateHandler(final Sprite ball, final Sprite endPoint){
		this.ball = ball;
		this.endPoint = endPoint;
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
