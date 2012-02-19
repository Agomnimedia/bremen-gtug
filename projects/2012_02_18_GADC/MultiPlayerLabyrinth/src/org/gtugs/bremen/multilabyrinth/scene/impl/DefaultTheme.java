package org.gtugs.bremen.multilabyrinth.scene.impl;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;
import org.gtugs.bremen.multilabyrinth.scene.api.Theme;

import android.content.Context;

public class DefaultTheme implements Theme{

	
	private BitmapTextureAtlas bitmapTextureAtlas;

	private ITextureRegion ballRegion;
	
	private ITextureRegion trapRegion;
	
	private ITextureRegion startRegion;
	
	private ITextureRegion endRegion;
	
	private ITextureRegion particleRegion;
	
	private Sound trapSound;
	
	private Sound hitWallSound;

	private Sound finishBallSound;
	
	public DefaultTheme(final Context context, final TextureManager textureManager, final SoundManager soundManager){
		/* Textures. */
		this.bitmapTextureAtlas = new BitmapTextureAtlas(textureManager, 64, 96, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		/* TextureRegions. */
		this.ballRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, context, "ball.png", 0, 0);
		this.trapRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, context, "trap.png", 32, 0);
		this.startRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, context, "start.png", 0, 32);
		this.endRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, context, "end.png", 0, 64);
		this.particleRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, context, "particle_fire.png", 32, 64);
		
		createSounds(context, soundManager);
	}
	
	private void createSounds(final Context context, final SoundManager soundManager) {
        SoundFactory.setAssetBasePath("sfx/");
        try {
                this.trapSound = SoundFactory.createSoundFromAsset(soundManager, context, "trap.ogg");
                this.hitWallSound = SoundFactory.createSoundFromAsset(soundManager, context, "hitWall.ogg");
                this.finishBallSound = SoundFactory.createSoundFromAsset(soundManager, context, "finishBall.ogg");
        } catch (final IOException e) {
                Debug.e(e);
        }
	}

	@Override
	public BitmapTextureAtlas getBitmapTextureAtlas() {
		return this.bitmapTextureAtlas;
	}

	@Override
	public ITextureRegion getBallRegion() {
		return this.ballRegion;
	}

	@Override
	public ITextureRegion getTrapRegion() {
		return this.trapRegion;
	}

	@Override
	public ITextureRegion getStartRegion() {
		return this.startRegion;
	}

	@Override
	public ITextureRegion getEndRegion() {
		return this.endRegion;
	}

	@Override
	public ITextureRegion getParticleRegion() {
		return this.particleRegion;
	}

	@Override
	public Sound getTrapSound() {
		return this.trapSound;
	}

	@Override
	public Sound getHitWallSound() {
		return this.hitWallSound;
	}

	@Override
	public Sound getFinishBallSound() {
		return this.finishBallSound;
	}

	@Override
	public void loadTheme(final Engine engine) {
		// load atlas
		engine.getTextureManager().loadTexture(this.bitmapTextureAtlas);
	}
}