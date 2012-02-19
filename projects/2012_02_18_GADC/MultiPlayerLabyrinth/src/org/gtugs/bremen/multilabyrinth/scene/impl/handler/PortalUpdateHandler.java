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
	
	// Completely not working yet !! 
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		if(ball.collidesWith(portal)) {
			portal.setColor(1, 0, 0);
			
			Log.d("PortalUpdaterHandler", "ball getSceneCenterCoordinatesX " + ball.getSceneCenterCoordinates()[0]);
			Log.d("PortalUpdaterHandler", "ball getSceneCenterCoordinatesY " + ball.getSceneCenterCoordinates()[1]);
			
			float portalX1 = portal.getX1();
			float portalY1 = portal.getY1();
			float portalX2 = portal.getX2();
			float portalY2 = portal.getY2();
//			
			float collisionPosX = ball.getSceneCenterCoordinates()[0];
			float collisionPosY = ball.getSceneCenterCoordinates()[1];
			collisionPosY = collisionPosY < portalY1 ? portalY1 : (collisionPosY < portalY2 ? collisionPosY : portalY2);
			
			// entry ration from Y1 (so Y1 = 0%, Y2 = 100%)
			float rawEntry = 1 - ((portalY2 - collisionPosY) / (portalY2-portalY1)) + 0.3f;

			Log.d("PortalUpdaterHandler", "RAW: collisionY = " + collisionPosY);
			Log.d("PortalUpdaterHandler", "RAW: portalY2 = " + portalY2);
			Log.d("PortalUpdaterHandler", "RAW: portalY1 = " + portalY1);
			Log.d("PortalUpdaterHandler", "RAW: rawEntry = 1 - CY / (PY2-PY1) + 0.3 = " + rawEntry);
			float entry = rawEntry < 0 ? 0 : (rawEntry < 1 ? rawEntry : 1);
			Log.d("PortalUpdaterHandler", "entry = " + entry);
			
			// Also necessary: direction (angle), velocity
			if(this.networkCommunication != null) {
				this.networkCommunication.sendPortalCollision("12345", 50, entry, 30);
			}
			
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
