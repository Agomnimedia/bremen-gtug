package org.gtugs.bremen.multilabyrinth.network.api;

import java.io.IOException;
import java.util.List;

import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;

public interface NetworkCommunication {

	/**
	 * Sends the level information to the clients.
	 * 
	 * @param informations
	 */
	void sendLevelInformation(List<LevelInformation> informations) throws IOException;
	
	/**
	 * Send the information about a portal collision to the server (or if at server device to all clients).
	 * 
	 * @param id
	 * 		given portal id
	 * @param velocity
	 * 		given velocity
	 * @param entry
	 * 		given entry point as a rate
	 * @param direction
	 * 		given direction
	 */
	void sendPortalCollision(String id, float velocity, float entry, float direction);
	
	/**
	 * Sends the information about an endpoint collision to the server (or if a server device to all clients)
	 */
	void sendEndPointCollision();
}