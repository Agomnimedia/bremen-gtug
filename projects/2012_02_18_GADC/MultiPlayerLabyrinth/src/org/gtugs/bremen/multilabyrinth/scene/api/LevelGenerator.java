package org.gtugs.bremen.multilabyrinth.scene.api;

import java.util.List;

public interface LevelGenerator {

	/**
	 * Gets information about the level. It returns a {@link LevelInformation} for the players.
	 * 
	 * Player one gets the first {@link LevelInformation}, player two the second etc.
	 * 
	 * @return {@link List} of {@link LevelInformation}
	 */
	List<LevelInformation> getLevelinformation();
}
