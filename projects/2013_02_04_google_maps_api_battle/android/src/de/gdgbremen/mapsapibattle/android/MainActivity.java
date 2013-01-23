package de.gdgbremen.mapsapibattle.android;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import de.gdgbremen.mapsapibattle.library.AbstractMainActivity;

public class MainActivity extends AbstractMainActivity {

	private SupportMapFragment fragment;

	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (savedInstanceState == null) {
			final FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			final GoogleMapOptions options = new GoogleMapOptions();

			// TODO das in setting oder so angeben lassen?
			// options.zoomControlsEnabled(true);
			// options.zoomGesturesEnabled(true);
			fragment = SupportMapFragment.newInstance(options);
			transaction.add(R.id.singlefragment, fragment);
			transaction.commit();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		map = fragment.getMap();
		showTest();
	}

	private void showTest() {
		float zoom = 18.0f;
		if (zoom > map.getMaxZoomLevel()) {
			zoom = map.getMaxZoomLevel();
		}
		// TODO auswählbar in eigenen dialog o.ä
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		// map.setMapType(GoogleMap.MAP_TYPE_NONE);
		// map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		final CameraPosition position = new CameraPosition.Builder().zoom(zoom)
				.target(new LatLng(53.075858, 8.80772)).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
	}

	@Override
	protected void showAbout() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showMarker() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void hideMarker() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showOverlays() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void hideOverlays() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showPosition() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void hidePosition() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showRoute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void hideRoute() {
		// TODO Auto-generated method stub

	}
}
