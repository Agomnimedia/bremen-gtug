package org.gtugs.bremen.multilabyrinth.gui.dialog;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.gtugs.bremen.multilabyrinth.gui.NetworkSearchingActivity;
import org.gtugs.bremen.multilabyrinth.gui.R;

import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ConnectionWaitingDialog extends DialogFragment {
	private UdpSending udpSending;

	public static ConnectionWaitingDialog newInstance() {
		ConnectionWaitingDialog f = new ConnectionWaitingDialog();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.waiting);
		setCancelable(false);
		View v = inflater
				.inflate(R.layout.connection_waiting, container, false);
		Button butCancel = (Button) v.findViewById(R.id.buttonCancel);
		Button butStart = (Button) v.findViewById(R.id.buttonStart);

		butCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				udpSending.cancel(true);
				dismiss();
				final Intent intent = new Intent(getActivity(),
						NetworkSearchingActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});

		butStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				udpSending.cancel(true);
				dismiss();
				// TODO start game
				Toast toast = Toast.makeText(getActivity()
						.getApplicationContext(), "game is starting ...",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		udpSending = new UdpSending();
		udpSending.execute();
	}

	@Override
	public void onStop() {
		super.onStop();
		if (udpSending != null && !udpSending.isCancelled()) {
			udpSending.cancel(true);
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
			WifiManager wifi = (WifiManager) getActivity().getSystemService(
					Context.WIFI_SERVICE);
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
