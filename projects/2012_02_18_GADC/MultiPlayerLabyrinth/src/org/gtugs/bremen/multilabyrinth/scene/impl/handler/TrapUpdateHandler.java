package org.gtugs.bremen.multilabyrinth.scene.impl.handler;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.gtugs.bremen.multilabyrinth.scene.api.RemovePhysicsHandler;

import android.graphics.Point;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class TrapUpdateHandler implements IUpdateHandler {

	private final Sprite ball;
	
	private final Sprite trap;
	
	private final Sound trapSound;
	
	private boolean wasFallen = false;

	private final RemovePhysicsHandler removePhysicsHandler;
	
	private final float x;
	private final float y;
	
	public TrapUpdateHandler(final Sprite ball, final Sprite trap, final Sound trapSound, RemovePhysicsHandler removePhysicsHandler){
		this.ball = ball;
		this.trap = trap;
		this.trapSound = trapSound;
		this.removePhysicsHandler = removePhysicsHandler;
		this.x = trap.getX() + trap.getWidth()/2;
		this.y = trap.getY() + trap.getHeight()/2;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(!wasFallen && ball.collidesWith(trap)) {
			// TODO Sound
			if(ball.contains(x, y)) {
				trapSound.play();
				ScaleModifier modifier = new ScaleModifier(2, 1, 0);
				ball.registerEntityModifier(modifier);
				wasFallen = true;
				removePhysicsHandler.removePhysics(ball);
				ball.setPosition(trap.getX(), trap.getY());
				// TODO: Verloren!!!!!
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
