package org.gtugs.bremen.multilabyrinth.gui;

import org.gtugs.bremen.multilabyrinth.gui.fragment.NetworkSearchingFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class NetworkSearchingActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singlefragment_activity);

		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		NetworkSearchingFragment fragment = new NetworkSearchingFragment();
		transaction.add(R.id.singlefragment, fragment);
		transaction.commit();
	}
}