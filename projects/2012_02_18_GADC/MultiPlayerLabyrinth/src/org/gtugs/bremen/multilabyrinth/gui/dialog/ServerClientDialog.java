package org.gtugs.bremen.multilabyrinth.gui.dialog;

import org.gtugs.bremen.multilabyrinth.gui.NetworkSearchingActivity;
import org.gtugs.bremen.multilabyrinth.gui.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ServerClientDialog extends DialogFragment {

	public static ServerClientDialog newInstance() {
		ServerClientDialog f = new ServerClientDialog();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.choose_networkmode);
		setCancelable(false);
		View v = inflater.inflate(R.layout.server_client_decision, container,
				false);
		Button butClient = (Button) v.findViewById(R.id.buttonClient);
		Button butServer = (Button) v.findViewById(R.id.buttonServer);

		butClient.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((NetworkSearchingActivity) getActivity()).startSearching();
				dismiss();
			}
		});

		butServer.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
//				final Intent intent = new Intent(getActivity(),
//						GameActivity.class);
//				startActivity(intent);
				getActivity().finish();
			}
		});

		return v;
	}

}
