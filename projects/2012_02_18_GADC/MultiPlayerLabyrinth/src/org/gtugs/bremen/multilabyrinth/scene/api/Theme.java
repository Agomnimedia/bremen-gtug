package org.gtugs.bremen.multilabyrinth.scene.api;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

public interface Theme {

	BitmapTextureAtlas getBitmapTextureAtlas();

	ITextureRegion getBallRegion();
	
	ITextureRegion getTrapRegion();
	
	ITextureRegion getStartRegion();
	
	ITextureRegion getEndRegion();
	
	ITextureRegion getParticleRegion();
	
	void loadTheme(Engine engine);

	Sound getTrapSound();

	Sound getHitWallSound();
}
