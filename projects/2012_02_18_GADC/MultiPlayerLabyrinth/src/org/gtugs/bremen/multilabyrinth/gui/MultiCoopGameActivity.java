package org.gtugs.bremen.multilabyrinth.gui;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
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
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunication;
import org.gtugs.bremen.multilabyrinth.network.api.NetworkCommunicationFactory;
import org.gtugs.bremen.multilabyrinth.network.impl.DefaultNetworkCommunication;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelCreator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.api.Theme;
import org.gtugs.bremen.multilabyrinth.scene.impl.DefaultLevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.impl.DefaultTheme;
import org.gtugs.bremen.multilabyrinth.scene.impl.LevelCreatorImpl;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;

public class MultiCoopGameActivity extends SimpleBaseGameActivity implements
		IAccelerationListener {

	// Dialog ids
	private static final int DIALOG_CONNECTING_ID = 0;

	// Camera sizes
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	private LevelGenerator levelGenerator = null;

	private LevelCreator levelCreator = null;

	private Theme theme = null;

	private Dialog connectingDialog;
	
	private UdpSending udpSending;
	
	private NetworkCommunication networkCommunication = null;
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DIALOG_CONNECTING_ID:
			dialog = createConnectingDialog();
			break;
		default:
			dialog = super.onCreateDialog(id);
			break;
		}
		return dialog;
	}

	private Dialog createConnectingDialog() {
		connectingDialog = new Dialog(this);
		connectingDialog.setContentView(R.layout.connection_waiting);
		connectingDialog.setTitle(R.string.waiting);
		connectingDialog.setCancelable(false);
		Button butCancel = (Button) connectingDialog.findViewById(R.id.buttonCancel);
		Button butStart = (Button) connectingDialog.findViewById(R.id.buttonStart);

		udpSending = new UdpSending();
		udpSending.execute();
		
		butCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				udpSending.cancel(true);
				connectingDialog.dismiss();
				final Intent intent = new Intent(getApplicationContext(),
						MultiPlayerLabyrinthActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		butStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				udpSending.cancel(true);
				connectingDialog.dismiss();
				// TODO start game
				Toast toast = Toast.makeText(getApplicationContext(), "game is starting ...",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		return connectingDialog;
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		if(NetworkCommunicationFactory.get() == null) { // should be a server
			this.networkCommunication = new DefaultNetworkCommunication();
			this.showDialog(DIALOG_CONNECTING_ID);
		}
		else {
			this.networkCommunication = NetworkCommunicationFactory.get();
		}

		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
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
		this.levelCreator = new LevelCreatorImpl(
				this.getVertexBufferObjectManager(), this.theme);
		// get information from levelGenerator
		final List<LevelInformation> informations = this.levelGenerator
				.getLevelinformation();

		informations.add(informations.get(0));

		Scene scene = this.levelCreator.createScene(informations.get(0));
		// TODO send other levelinformations to components

		//this.levelCreator.addBallToScene(scene, 100, 100);

		return scene;
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		if (this.levelCreator != null) {
			levelCreator.setAccelerationData(pAccelerationData);
		}
	}

	private class UdpSending extends AsyncTask<Void, Void, Void> {
		final static int UDP_PORT = 1234;
		final static long SENDING_INTERVAL = 1000;

		@Override
		protected synchronized Void doInBackground(Void... params) {
			final DatagramSocket socket;
			final InetAddress broadcast;
			final String msg = "I'm here!";
			DatagramPacket paket;
			try {
				socket = new DatagramSocket();
				broadcast = getBroadcastAddress();
				Log.i("BROADCAST", broadcast.getHostAddress());
				while (true) {
					if (isCancelled()) {
						break;
					}
					paket = new DatagramPacket(msg.getBytes(), msg.length(),
							broadcast, UDP_PORT);
					socket.send(paket);
					Log.i("BROADCAST", "UDP message was sent.");
					wait(SENDING_INTERVAL);
				}
				socket.close();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		private InetAddress getBroadcastAddress() throws IOException {
			WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			DhcpInfo dhcp = wifi.getDhcpInfo();
			// TODO handle null somehow

			int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
			byte[] quads = new byte[4];
			for (int k = 0; k < 4; k++)
				quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
			return InetAddress.getByAddress(quads);
		}
	}
}