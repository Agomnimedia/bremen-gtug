package de.dsi8.android.andenginepresentation;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.ui.activity.BaseGameActivity;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 * (c) 2012 Henrik Voß, Sven Nobis
 *
 * @author Nicolas Gramlich
 * @since 18:47:08 - 19.03.2010
 */
public class Iteration1Activity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Scene mScene;

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(
													true, 
													ScreenOrientation.LANDSCAPE, 
													new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), 
													camera
												);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		return new Engine(engineOptions);
	}

	@Override
	public Scene onLoadScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new ColorBackground(0, 0, 0));

		final int margineTop = 25;
		
		final Shape ground = new Rectangle(0, CAMERA_HEIGHT - 2, CAMERA_WIDTH, 2);
		final Shape roof = new Rectangle(0, margineTop, CAMERA_WIDTH, 2);
		final Shape left = new Rectangle(0, margineTop, 2, CAMERA_HEIGHT - margineTop);
		final Shape right = new Rectangle(CAMERA_WIDTH - 2, margineTop, 2, CAMERA_HEIGHT - margineTop);

		this.mScene.attachChild(ground);
		this.mScene.attachChild(roof);
		this.mScene.attachChild(left);
		this.mScene.attachChild(right);

		return this.mScene;
	}

	@Override
	public void onLoadResources() {
	}

	@Override
	public void onLoadComplete() {
	}
}
