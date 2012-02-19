package org.gtugs.bremen.multilabyrinth.gui.fragment;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.gtugs.bremen.multilabyrinth.gui.R;
import org.gtugs.bremen.multilabyrinth.gui.dialog.ServerClientDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NetworkSearchingFragment extends Fragment {
	final static long TIMEOUT = 3000;
	private UdpReceiving udpReceiving;
	private TreeMap<String, Long> serverList = new TreeMap<String, Long>();
	private ArrayAdapter<String> serverListAdapter;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.serverlist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final ListView listView = (ListView) getView().findViewById(
				R.id.serverList);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {

				// inform the childActivity about a itemclick
				// onItemClicked(serverList.get(position),
				// serverListAdapter.getItem(position));
			}
		});
		serverListAdapter = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(), android.R.layout.simple_list_item_1);
		listView.setAdapter(serverListAdapter);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ServerClientDialog clientServerDecision = ServerClientDialog
				.newInstance();
		clientServerDecision.show(ft, "cs_decision");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		if (udpReceiving != null && !udpReceiving.isCancelled()) {
			udpReceiving.cancel(true);
		}
		super.onStop();
	}

	public void startSearching() {
		udpReceiving = new UdpReceiving();
		udpReceiving.execute();
	}

	private void addServerToList(final String ipaddress) {
		if (serverList.containsKey(ipaddress)) {
			serverList.put(ipaddress, System.currentTimeMillis());
		} else {
			serverList.put(ipaddress, System.currentTimeMillis());
			serverListAdapter.add(ipaddress);
			serverListAdapter.notifyDataSetChanged();
		}
	}

	public void updateServerList() {
		TreeMap<String, Long> serverListNeu = new TreeMap<String, Long>();
		Set<Map.Entry<String, Long>> entrySet = serverList.entrySet();
		for (Map.Entry<String, Long> paar : entrySet) {
			if (paar.getValue() > System.currentTimeMillis() - TIMEOUT) {
				serverListNeu.put(paar.getKey(), paar.getValue());
			} else {
				serverListAdapter.remove(paar.getKey());
			}
		}
		serverList = serverListNeu;
		serverListAdapter.notifyDataSetChanged();
		startSearching();
	}

	private class UdpReceiving extends AsyncTask<Void, Void, String> {
		final static int UDP_PORT = 1234;

		@Override
		protected synchronized String doInBackground(Void... params) {
			final DatagramSocket socket;
			DatagramPacket paket;
			try {
				socket = new DatagramSocket(UDP_PORT);
				byte[] puffer = new byte[50];
				paket = new DatagramPacket(puffer, puffer.length);
				Log.i("RECEIVE", "start receiving ...");
				if (!socket.isClosed() && !isCancelled()) {
					try {
						socket.setSoTimeout(5000);
						socket.receive(paket);
						if(!isCancelled()) {
							final String text = new String(puffer, 0,
									paket.getLength());
							final String ipaddress = paket.getAddress()
									.getHostAddress();
							Log.i("RECEIVE", "received: " + text + ", " + ipaddress);
							socket.close();
							return ipaddress;
						}
					} catch (IOException e) {
						Log.i("RECEIVE", "nothing received, try again ...");
						// nothing to do, only timeout is arrived and nothing
						// received.
					}
				}
				socket.close();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				addServerToList(result);
			}
			updateServerList();
		}
	}
}