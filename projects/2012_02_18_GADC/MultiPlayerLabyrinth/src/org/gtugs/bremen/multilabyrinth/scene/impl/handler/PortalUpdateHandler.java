package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunication;

import android.util.Log;

public class PortalUpdateHandler implements IUpdateHandler{

	private final Sprite ball;
	
	private final Line portal;
	
	private final NetworkCommunication networkCommunication;
	
	public PortalUpdateHandler(final Sprite ball, final Line portal, final NetworkCommunication networkCommunication){
		super();
		this.ball = ball;
		this.portal = portal;
		this.networkCommunication = networkCommunication;
	}
	
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		if(ball.collidesWith(portal)) {
			portal.setColor(1, 0, 0);
			
//			Log.d("PortalUpdaterHandler", "ball getSkewCenterX " + ball.getSceneCenterCoordinates()[0]);
			Log.d("PortalUpdaterHandler", "ball getSkewCenterY " + ball.getSceneCenterCoordinates()[1]);
			
//			int collisionPosY
//			portal.
		} else {
			portal.setColor(0, 1, 0);
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		// Nothing to do ...
	}

}
