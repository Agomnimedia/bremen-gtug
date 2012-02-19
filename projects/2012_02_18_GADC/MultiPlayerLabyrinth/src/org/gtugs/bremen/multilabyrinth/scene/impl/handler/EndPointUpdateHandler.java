package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunication;
import org.gtugs.bremen.multilabyrinth.scene.api.RemovePhysicsHandler;

public class EndPointUpdateHandler extends BasePointUpdateHandler {
	
	private NetworkCommunication networkCommunication;
	
	public EndPointUpdateHandler(final Sprite ball, final Sprite endPoint, final Sound finishBallSound, final NetworkCommunication networkCommunication, final RemovePhysicsHandler removePhysicsHandler){
		super(ball, endPoint, finishBallSound, removePhysicsHandler);
		this.networkCommunication = networkCommunication;
	}

	@Override
	protected void onFallenIn() {
		// TODO networkCommunication
	}

}
