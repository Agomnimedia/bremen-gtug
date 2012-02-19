package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import android.util.Log;

public class PortalUpdateHandler implements IUpdateHandler{

	private final Sprite ball;
	
	private final Line portal;
	
	public PortalUpdateHandler(final Sprite ball, final Line portal){
		super();
		this.ball = ball;
		this.portal = portal;
	}
	
	@Override
	public void onUpdate(final float pSecondsElapsed) {
		if(ball.collidesWith(portal)) {
			portal.setColor(1, 0, 0);
		} else {
			portal.setColor(0, 1, 0);
		}
		
		System.out.println("HUHU 3");
		Log.d("lol", "HUHU 3 - lol");
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		// Nothing to do ...
	}

}
