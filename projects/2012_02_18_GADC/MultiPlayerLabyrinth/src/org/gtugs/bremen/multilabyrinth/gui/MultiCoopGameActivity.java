package org.gtugs.bremen.multilabyrinth.gui;

import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
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

import com.badlogic.gdx.math.Vector2;

public class MultiCoopGameActivity extends SimpleBaseGameActivity implements IAccelerationListener {
	
	// Dialog ids
	private static final int DIALOG_CHOOSE_SERVER_OR_CLIENT_ID = 0;
	private static final int DIALOG_ENTER_SERVER_IP_ID = DIALOG_CHOOSE_SERVER_OR_CLIENT_ID + 1;
	private static final int DIALOG_SHOW_SERVER_IP_ID = DIALOG_ENTER_SERVER_IP_ID + 1;
	
	// Camera sizes
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
			
	private LevelGenerator levelGenerator = null;
			
	private LevelCreator levelCreator = null;
	
	private Theme theme = null;
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.showDialog(DIALOG_CHOOSE_SERVER_OR_CLIENT_ID);
		
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions engineOptions) {
		return new Engine(engineOptions);
	}

	@Override
	protected void onCreateResources() {
		this.theme = new DefaultTheme(this, this.getTextureManager());
		this.theme.loadTheme(this.mEngine);
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
	protected Scene onCreateScene() {
		this.levelGenerator = new DefaultLevelGenerator(1);
		this.levelCreator = new LevelCreatorImpl(this.getVertexBufferObjectManager(), this.theme);
		// get information from levelGenerator
		final List<LevelInformation> informations = this.levelGenerator.getLevelinformation();
		
		informations.add(informations.get(0));
		
		Scene scene = this.levelCreator.createScene(informations.get(0));
		// TODO send other levelinformations to components
		
		this.levelCreator.addBallToScene(scene, 100, 100);
		
		return scene;
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
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