package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
