package org.gtugs.bremen.multilabyrinth.network.impl;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.server.SocketServer;
import org.andengine.extension.multiplayer.protocol.server.SocketServer.ISocketServerListener;
import org.andengine.extension.multiplayer.protocol.server.connector.ClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector.ISocketConnectionClientConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;
import org.andengine.util.debug.Debug;
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunication;
import org.gtugs.bremen.multilabyrinth.network.impl.messages.ConnectionCloseServerMessage;
import org.gtugs.bremen.multilabyrinth.network.impl.messages.LevelinformationMessage;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;

import android.util.Log;

public class DefaultNetworkCommunication implements NetworkCommunication {

	public static final short FLAG_MESSAGE_SERVER_CONNECTION_CLOSE = Short.MIN_VALUE;

	private static final short FLAG_LEVEL_INFORMATION = 1;

	private SocketServer<SocketConnectionClientConnector> mSocketServer;
	
	private ServerConnector<SocketConnection> mServerConnector;

	private final MessagePool<IMessage> mMessagePool = new MessagePool<IMessage>();

	private static final int SERVER_PORT = 3456;

	private final String serverIp;

	private final CommunicationEstablished communicationEstablished;
	
	private LevelInformation levelInformation = null;
	
	private List<LevelInformation> levelInformationsForServer = null;
	
	private final String ownIp;

	public DefaultNetworkCommunication(final String ip,
			final CommunicationEstablished communicationEstablished,
			final String ownIp)
			throws Throwable {
		this.ownIp = ownIp;
		this.communicationEstablished = communicationEstablished;

		this.serverIp = ip;

		this.initMessagePool();

		this.initClient();
		this.mSocketServer = null;
	}

	public DefaultNetworkCommunication(final String ownIp) {
		this.ownIp = ownIp;
		this.communicationEstablished = null;
		this.serverIp = null;
		this.initMessagePool();
		this.initServer();
		this.mServerConnector = null;
	}
	
	/**
	 * @return the levelInformation
	 */
	public LevelInformation getLevelInformation() {
		return levelInformation;
	}

	/**
	 * @param levelInformation the levelInformation to set
	 */
	public void setLevelInformation(LevelInformation levelInformation) {
		this.levelInformation = levelInformation;
	}

	private void initMessagePool() {
		this.mMessagePool.registerMessage(FLAG_LEVEL_INFORMATION,
				LevelinformationMessage.class);
		// this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_MOVE_FACE,
		// MoveFaceServerMessage.class);
	}

	private void initServer() {
		this.mSocketServer = new SocketServer<SocketConnectionClientConnector>(
				SERVER_PORT, new DefaultClientConnectorListener(),
				new DefaultServerStateListener()) {
			@Override
			protected SocketConnectionClientConnector newClientConnector(
					final SocketConnection pSocketConnection)
					throws IOException {
				return new SocketConnectionClientConnector(pSocketConnection);
			}
		};

		this.mSocketServer.start();
	}

	private void initClient() throws Throwable {
		this.mServerConnector = new SocketConnectionServerConnector(
				new SocketConnection(new Socket(this.serverIp, SERVER_PORT)),
				new DefaultServerConnectorListener());

		this.mServerConnector.registerServerMessage(
				FLAG_MESSAGE_SERVER_CONNECTION_CLOSE,
				ConnectionCloseServerMessage.class,
				new IServerMessageHandler<SocketConnection>() {

					@Override
					public void onHandleMessage(
							ServerConnector<SocketConnection> pServerConnector,
							IServerMessage pServerMessage) throws IOException {
						// TODO close connection
					}
				});

		this.mServerConnector.registerServerMessage(FLAG_LEVEL_INFORMATION,
				LevelinformationMessage.class,
				new IServerMessageHandler<SocketConnection>() {
					@Override
					public void onHandleMessage(
							final ServerConnector<SocketConnection> pServerConnector,
							final IServerMessage pServerMessage)
							throws IOException {
						final LevelinformationMessage levelInfoMessage = (LevelinformationMessage) pServerMessage;
						DefaultNetworkCommunication.this.communicationEstablished.communicationEstablished();
						
						for(final LevelInformation info : levelInfoMessage.get()){
							if(info.getIpAddress().equals(DefaultNetworkCommunication.this.ownIp)){
								DefaultNetworkCommunication.this.setLevelInformation(info);
								break;
							}
						}
					}
				});

		this.mServerConnector.getConnection().start();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private class DefaultServerConnectorListener implements
			ISocketConnectionServerConnectorListener {

		private static final String TAG = "DefaultServerConnectorListener";

		@Override
		public void onStarted(final ServerConnector<SocketConnection> pConnector) {
			Log.d(TAG, "CLIENT: Connected to server.");
		}

		@Override
		public void onTerminated(
				final ServerConnector<SocketConnection> pConnector) {
			Log.d(TAG, "CLIENT: Disconnected from Server...");
			// finish connection
		}
	}

	private class DefaultClientConnectorListener implements
			ISocketConnectionClientConnectorListener {

		private static final String TAG = "DefaultClientConnectorListener";

		@Override
		public void onStarted(final ClientConnector<SocketConnection> pConnector) {
			Log.d(TAG, "SERVER: Client connected: "
					+ pConnector.getConnection().getSocket().getInetAddress()
							.getHostAddress());
			
			for(LevelInformation info : DefaultNetworkCommunication.this.levelInformationsForServer){
				if(info.getIpAddress() == null || info.getIpAddress().isEmpty()){
					info.setIpAddress(pConnector.getConnection().getSocket().getInetAddress()
							.getHostAddress());
				}
			}
			final LevelinformationMessage levelInfoMessage = (LevelinformationMessage) DefaultNetworkCommunication.this.mMessagePool
					.obtainMessage(FLAG_LEVEL_INFORMATION);
			
			levelInfoMessage.set(DefaultNetworkCommunication.this.levelInformationsForServer);

			try {
				DefaultNetworkCommunication.this.mSocketServer.sendBroadcastServerMessage(levelInfoMessage);
			} catch (IOException e) {
				Log.e("DefaultClientConnectorListener", "IOException occured: "+e.getMessage());
			}

			DefaultNetworkCommunication.this.mMessagePool.recycleMessage(levelInfoMessage);
		}

		@Override
		public void onTerminated(
				final ClientConnector<SocketConnection> pConnector) {
			Log.d(TAG, "SERVER: Client disconnected: "
					+ pConnector.getConnection().getSocket().getInetAddress()
							.getHostAddress());
			// TODO don't do anything when game hasn't started yet
			// TODO stop game in case game started allready
		}
	}

	private class DefaultServerStateListener implements
			ISocketServerListener<SocketConnectionClientConnector> {

		private static final String TAG = "DefaultServerStateListener";

		@Override
		public void onStarted(
				final SocketServer<SocketConnectionClientConnector> pSocketServer) {
			Log.d(TAG, "SERVER: Started.");
		}

		@Override
		public void onTerminated(
				final SocketServer<SocketConnectionClientConnector> pSocketServer) {
			Log.d(TAG, "SERVER: Terminated.");
		}

		@Override
		public void onException(
				final SocketServer<SocketConnectionClientConnector> pSocketServer,
				final Throwable pThrowable) {
			Debug.e(pThrowable);
			Log.d(TAG, "SERVER: Exception: " + pThrowable);
		}
	}

	@Override
	public void sendLevelInformation(List<LevelInformation> informations)
			throws IOException {
		if (this.mSocketServer != null) {
			this.levelInformationsForServer = informations;
			final LevelinformationMessage levelInfoMessage = (LevelinformationMessage) this.mMessagePool
					.obtainMessage(FLAG_LEVEL_INFORMATION);
			
			levelInfoMessage.set(informations);

			this.mSocketServer.sendBroadcastServerMessage(levelInfoMessage);

			this.mMessagePool.recycleMessage(levelInfoMessage);
		}
	}

	public interface CommunicationEstablished {
		public void communicationEstablished();
	}

	@Override
	public void sendPortalCollision(String id, float velocity, float entry,
			float direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEndPointCollision() {
		// TODO Auto-generated method stub
		
	}
}