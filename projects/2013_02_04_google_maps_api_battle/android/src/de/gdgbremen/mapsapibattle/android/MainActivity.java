package de.gdgbremen.mapsapibattle.android;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.common.GooglePlayServicesUtil;

import de.gdgbremen.mapsapibattle.android.fragments.MyMapFragment;
import de.gdgbremen.mapsapibattle.library.AbstractMainActivity;

public class MainActivity extends AbstractMainActivity {

	private MyMapFragment fragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		

		if (getSupportFragmentManager().findFragmentByTag("mapFragment") == null) {
			fragment = MyMapFragment.newInstance();
			transaction.add(R.id.singlefragment, fragment, "mapFragment");
		} else {
			fragment = (MyMapFragment) getSupportFragmentManager()
					.findFragmentByTag("mapFragment");
		}
		transaction.commit();

		if (savedInstanceState == null) {
			// TODO ?!
		}
	}

	@Override
	protected void showAbout() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showMarker() {
		fragment.marker(true);
	}

	@Override
	protected void hideMarker() {
		fragment.marker(false);
	}

	@Override
	protected void showOverlays() {
		fragment.overlays(true);
	}

	@Override
	protected void hideOverlays() {
		fragment.overlays(false);
	}

	@Override
	protected void showPosition() {
		fragment.position(true);
	}

	@Override
	protected void hidePosition() {
		fragment.position(false);
	}

	@Override
	protected void showRoute() {
		fragment.route(true);
	}

	@Override
	protected void hideRoute() {
		fragment.route(false);
	}
}