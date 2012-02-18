package org.gtugs.bremen.multilabyrinth.scene.api;

import org.andengine.entity.scene.Scene;


public interface LevelCreator {
	
	public Scene createScene(LevelInformation levelInfo);
}
