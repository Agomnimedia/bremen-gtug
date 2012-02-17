package org.gtugs.bremen.multilabyrinth.network.api;

import java.util.List;

import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;

public interface NetworkManager {

	/**
	 * Sends the level information to the clients.
	 * 
	 * @param informations
	 */
	void sendLevelInformation(List<LevelInformation> informations);
}
