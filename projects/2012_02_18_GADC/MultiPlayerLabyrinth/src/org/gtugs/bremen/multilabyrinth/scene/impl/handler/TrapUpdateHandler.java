package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;
import org.gtugs.bremen.multilabyrinth.scene.api.RemoveBallHandler;

public class TrapUpdateHandler extends BasePointUpdateHandler {
	
	public TrapUpdateHandler(final Sprite ball, final Sprite trap, final Sound trapSound, RemoveBallHandler removePhysicsHandler){
		super(ball, trap, trapSound, removePhysicsHandler, true);
	}

	@Override
	protected void onFallenIn() {
		// Nothing special to do
	}

}
