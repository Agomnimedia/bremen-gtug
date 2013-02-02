package de.gdgbremen.mapsapibattle.javascript;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import de.gdgbremen.mapsapibattle.library.AbstractMainActivity;
import de.gdgbremen.mapsapibattle.library.MapType;

public class MainActivity extends AbstractMainActivity {
	private MainFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			final FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			fragment = new MainFragment();
			transaction.add(R.id.singlefragment, fragment);
			transaction.commit();
		} else {
			fragment = (MainFragment) getSupportFragmentManager().findFragmentById(
					R.id.singlefragment);
		}
	}

	@Override
	protected void showAbout() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void showMarker() {
		fragment.showMarker();
	}

	@Override
	protected void hideMarker() {
		fragment.hideMarker();
	}

	@Override
	protected void showOverlays() {
		fragment.showOverlays();
	}

	@Override
	protected void hideOverlays() {
		fragment.hideOverlays();
	}

	@Override
	protected void showPosition() {
		fragment.showPosition();
	}

	@Override
	protected void hidePosition() {
		fragment.hidePosition();
	}

	@Override
	protected void showRoute() {
		fragment.showPosition();
	}

	@Override
	protected void hideRoute() {
		fragment.hideRoute();
	}

	@Override
	protected void setMapType(MapType mapType) {
		fragment.setMapType(mapType);
	}

}
