package org.gtugs.bremen.multilabyrinth.gui.dialog;

import org.gtugs.bremen.multilabyrinth.gui.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConnectionWaitingDialog extends DialogFragment {

	public static ConnectionWaitingDialog newInstance() {
		ConnectionWaitingDialog f = new ConnectionWaitingDialog();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.waiting);
		setCancelable(false);
		View v = inflater.inflate(R.layout.connection_init_waiting, container,
				false);
		return v;
	}
}
