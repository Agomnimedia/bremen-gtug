package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunication;
import org.gtugs.bremen.multilabyrinth.scene.api.RemovePhysicsHandler;

public class EndPointUpdateHandler implements IUpdateHandler {

	private final Sprite ball;
	
	private final Sprite endPoint;
	
	private NetworkCommunication networkCommunication;
	
	private final Sound finishBallSound;
	
	private boolean wasFallen = false;

	private final RemovePhysicsHandler removePhysicsHandler;
	
	private final float x;
	private final float y;
	
	public EndPointUpdateHandler(final Sprite ball, final Sprite endPoint, final Sound finishBallSound, final NetworkCommunication networkCommunication, final RemovePhysicsHandler removePhysicsHandler){
		this.ball = ball;
		this.endPoint = endPoint;
		this.networkCommunication = networkCommunication;
		this.finishBallSound = finishBallSound;
		this.removePhysicsHandler = removePhysicsHandler;
		this.x = endPoint.getX() + endPoint.getWidth()/2;
		this.y = endPoint.getY() + endPoint.getHeight()/2;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(!wasFallen && ball.collidesWith(endPoint)) {
			if(ball.contains(x, y)) {
				finishBallSound.play();
				ScaleModifier modifier = new ScaleModifier(2, 1, 0);
				ball.registerEntityModifier(modifier);
				wasFallen = true;
				removePhysicsHandler.removePhysics(ball);
				ball.setPosition(endPoint.getX(), endPoint.getY());
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
