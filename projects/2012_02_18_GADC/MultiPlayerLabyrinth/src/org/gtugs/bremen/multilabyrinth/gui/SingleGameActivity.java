package org.gtugs.bremen.multilabyrinth.gui;

import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.impl.DefaultLevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.impl.LevelCreatorImpl;

import com.badlogic.gdx.math.Vector2;

public class SingleGameActivity extends SimpleBaseGameActivity implements IAccelerationListener{
		
		// Camera sizes
		private static final int CAMERA_WIDTH = 720;
		private static final int CAMERA_HEIGHT = 480;
		
		private LevelGenerator levelGenerator = null;
		
		private LevelCreator levelCreator = null;
		
		private BitmapTextureAtlas bitmapTextureAtlas;

		private ITextureRegion ballRegion;
		
		private ITextureRegion trapRegion;
		
		private ITextureRegion startRegion;
		
		private ITextureRegion endRegion;
		
		private ITextureRegion particleRegion;


		// LIFECYCLE
		//	launch game
		//	1) onCreateEngine()
		//  onCreate
		//	2) onResume()
		//	3) onCreateResources()
		//	4) onCreateScene()
		//	5) onLoadComplete() [??]
		//	6) onGameResumed()
		//
		//
		//	during game, go to homescreen
		//	1) onPause()
		//
		//	return to game
		//	1) onResume()
		//	2) onGameResumed()
		//
		//	exit game with this.finish()
		//	1) onPause()
		//	2) onDestroy()
		
		@Override
		public EngineOptions onCreateEngineOptions() {
			final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

			return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		}
		
		@Override
		public Engine onCreateEngine(EngineOptions engineOptions) {
			return new Engine(engineOptions);
		}

		@Override
		protected void onCreateResources() {
			/* Textures. */
			this.bitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 96, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

			/* TextureRegions. */
			this.ballRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, this, "ball.png", 0, 0);
			this.trapRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, this, "trap.png", 32, 0);
			this.startRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, this, "start.png", 0, 32);
			this.endRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, this, "end.png", 0, 64);
			this.particleRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, this, "particle_fire.png", 32, 64);
			
			// load atlas
			this.mEngine.getTextureManager().loadTexture(this.bitmapTextureAtlas);
		}

		@Override
		protected Scene onCreateScene() {
			this.levelGenerator = new DefaultLevelGenerator(11);
			this.levelCreator = new LevelCreatorImpl(this.getVertexBufferObjectManager(), this.ballRegion, 
					this.trapRegion, this.startRegion, this.endRegion, this.particleRegion);
			// get information from levelGenerator
			final List<LevelInformation> informations = this.levelGenerator.getLevelinformation();
			
			Scene scene = this.levelCreator.createScene(informations.get(0));
			this.levelCreator.addBallToScene(scene, 100, 100);
			
			return scene;
		}
		
		@Override
		public void onResumeGame() {
			super.onResumeGame();
			this.enableAccelerationSensor(this);
		}
		
		@Override
		public void onPauseGame() {
			super.onPauseGame();
			this.disableAccelerationSensor();
		}

		@Override
		public void onAccelerationAccuracyChanged(
				AccelerationData pAccelerationData) {
			// nothing to implement yet
		} 

		@Override
		public void onAccelerationChanged(AccelerationData pAccelerationData) {
			if(this.levelCreator != null){
				final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
				this.levelCreator.setGravity(gravity);
				Vector2Pool.recycle(gravity);
			}
		}
}