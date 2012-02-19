package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunication;
import org.gtugs.bremen.multilabyrinth.scene.api.RemoveBallHandler;

public class EndPointUpdateHandler extends BasePointUpdateHandler {
	
	private NetworkCommunication networkCommunication;
	
	public EndPointUpdateHandler(final Sprite ball, final Sprite endPoint, final Sound finishBallSound, final NetworkCommunication networkCommunication, final RemoveBallHandler removePhysicsHandler){
		super(ball, endPoint, finishBallSound, removePhysicsHandler, false);
		this.networkCommunication = networkCommunication;
	}

	@Override
	protected void onFallenIn() {
		// TODO networkCommunication
	}

}
