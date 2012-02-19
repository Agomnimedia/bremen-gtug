package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.gtugs.bremen.multilabyrinth.scene.api.RemoveBallHandler;

public abstract class BasePointUpdateHandler implements IUpdateHandler {
	
	private final Sprite ball;
	
	private final Sprite point;
	
	private final Sound sound;
	
	private boolean wasFallen = false;

	private final RemoveBallHandler removeBallHandler;
	
	private final float x;
	private final float y;
	
	private final boolean addNewBallOnRemove;
	
	public BasePointUpdateHandler(final Sprite ball, final Sprite point, final Sound sound, final RemoveBallHandler removeBallHandler, final boolean addNewBallOnRemove){
		this.ball = ball;
		this.point = point;
		this.sound = sound;
		this.removeBallHandler = removeBallHandler;
		this.addNewBallOnRemove = addNewBallOnRemove;
		this.x = point.getX() + point.getWidth()/2;
		this.y = point.getY() + point.getHeight()/2;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(!wasFallen && ball.collidesWith(point)) {
			if(ball.contains(x, y)) {
				sound.play();
				ScaleModifier modifier = new ScaleModifier(2, 1, 0);
				ball.registerEntityModifier(modifier);
				wasFallen = true;
				removeBallHandler.removeBall(ball, addNewBallOnRemove);
				ball.setPosition(point.getX(), point.getY());
				onFallenIn();
			}
		}
	}

	@Override
	public void reset() {
	}
	
	protected abstract void onFallenIn();
}
