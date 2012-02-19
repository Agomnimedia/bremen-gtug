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
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.api.Theme;
import org.gtugs.bremen.multilabyrinth.scene.impl.DefaultLevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.impl.DefaultTheme;
import org.gtugs.bremen.multilabyrinth.scene.impl.LevelCreatorImpl;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.math.Vector2;

public class SingleGameActivity extends SimpleBaseGameActivity implements IAccelerationListener{
		
		// Camera sizes
		private static final int CAMERA_WIDTH = 720;
		private static final int CAMERA_HEIGHT = 480;
		
		private LevelGenerator levelGenerator = null;
		
		private LevelCreator levelCreator = null;
		
		private Theme theme = null;
				
		private int mode = 1;

		@Override
		protected void onCreate(Bundle pSavedInstanceState) {
			super.onCreate(pSavedInstanceState);
			
			if(Build.VERSION.SDK_INT > 10) {
				this.mRenderSurfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
			}
			
			
			this.mode = this.getIntent().getExtras().getInt(MultiPlayerLabyrinthActivity.MODE_EXTRA_NAME);
		}
		
		@Override
		public EngineOptions onCreateEngineOptions() {
			final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
			EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
			options.getAudioOptions().setNeedsSound(true);
			return options;
		}
		
		@Override
		public Engine onCreateEngine(EngineOptions engineOptions) {
			return new Engine(engineOptions);
		}

		@Override
		protected void onCreateResources() {
			this.theme = new DefaultTheme(this, this.getTextureManager(), this.getSoundManager());
			this.theme.loadTheme(this.mEngine);
		}

		@Override
		protected Scene onCreateScene() {
			this.levelGenerator = new DefaultLevelGenerator(this.mode);
			this.levelCreator = new LevelCreatorImpl(this.getVertexBufferObjectManager(), this.theme, null);
			// get information from levelGenerator
			final List<LevelInformation> informations = this.levelGenerator.getLevelinformation();
			
			Scene scene = this.levelCreator.createScene(informations.get(0));
			final float[] resetPoint = informations.get(0).getResetPoint(); 
			this.levelCreator.addBallToScene(scene, resetPoint[0], resetPoint[1]);
			
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
				final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX()*10, pAccelerationData.getY()*10);
				this.levelCreator.setAccelerationData(pAccelerationData);
				Vector2Pool.recycle(gravity);
			}
		}
}