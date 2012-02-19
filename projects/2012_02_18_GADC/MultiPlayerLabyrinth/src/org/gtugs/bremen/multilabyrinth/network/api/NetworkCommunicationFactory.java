package org.gtugs.bremen.multilabyrinth.network.api;

public class NetworkCommunicationFactory {
	private static NetworkCommunication networkCommunication = null;

	/**
	 * @return the networkCommunication
	 */
	public static NetworkCommunication get() {
		return networkCommunication;
	}

	/**
	 * @param networkCommunication the networkCommunication to set
	 */
	public static void set(NetworkCommunication pNetworkCommunication) {
		networkCommunication = pNetworkCommunication;
	}
	
}
