package org.gtugs.bremen.multilabyrinth;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.gtugs.bremen.multilabyrinth.scene.api.Element;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.api.WallElement;
import org.gtugs.bremen.multilabyrinth.scene.impl.LevelCreatorImpl;

public class SingleGameActivity extends SimpleBaseGameActivity{
		
		// Camera sizes
		private static final int CAMERA_WIDTH = 720;
		private static final int CAMERA_HEIGHT = 480;
		
//		private LevelGenerator levelGenerator;
		
		private LevelCreator levelCreator = null;
		
		public SingleGameActivity(){
//			this.levelGenerator = null;
			
			
		}
		


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
			// TODO Auto-generated method stub
			
		}

		@Override
		protected Scene onCreateScene() {
			// get information from levelGenerator
//			final List<LevelInformation> informations = this.levelGenerator.getLevelinformation();
			
			this.levelCreator = new LevelCreatorImpl(this.getVertexBufferObjectManager());
			
			final List<LevelInformation> informations = new ArrayList<LevelInformation>();
			final List<Element> elementList = new ArrayList<Element>();
			elementList.add(new WallElement(5, 25, 5, CAMERA_HEIGHT - 25));
			final LevelInformation levelInformation = new LevelInformation(elementList);
			informations.add(levelInformation);
			return this.levelCreator.createScene(informations.get(0));
		}

}
