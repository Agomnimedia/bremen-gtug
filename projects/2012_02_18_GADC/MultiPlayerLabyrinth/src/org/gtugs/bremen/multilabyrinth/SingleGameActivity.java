package org.gtugs.bremen.multilabyrinth;

import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.impl.DefaultLevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.impl.LevelCreatorImpl;

public class SingleGameActivity extends SimpleBaseGameActivity{
		
		// Camera sizes
		private static final int CAMERA_WIDTH = 720;
		private static final int CAMERA_HEIGHT = 480;
		
		private LevelGenerator levelGenerator = null;
		
		private LevelCreator levelCreator = null;
		
		private BitmapTextureAtlas bitmapTextureAtlas;

		private TiledTextureRegion ballTextureRegion;


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
			this.bitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

			/* TextureRegions. */
			this.ballTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.bitmapTextureAtlas, this, "ball.png", 0, 32, 1, 1);
			this.mEngine.getTextureManager().loadTexture(this.bitmapTextureAtlas);
		}

		@Override
		protected Scene onCreateScene() {
			this.levelGenerator = new DefaultLevelGenerator(1);
			this.levelCreator = new LevelCreatorImpl(this.getVertexBufferObjectManager(), this.ballTextureRegion);
			// get information from levelGenerator
			final List<LevelInformation> informations = this.levelGenerator.getLevelinformation();
			
			informations.add(informations.get(0));
			
			
			Scene scene = this.levelCreator.createScene(informations.get(0));
			this.levelCreator.addBallToScene(scene, 30, 30);
			
			return scene;
		}
}