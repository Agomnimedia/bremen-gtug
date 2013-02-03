package de.gdgbremen.mapsapibattle.android;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;

import de.gdgbremen.mapsapibattle.android.fragments.City;
import de.gdgbremen.mapsapibattle.android.fragments.ControlsFragment;
import de.gdgbremen.mapsapibattle.android.fragments.MyMapFragment;
import de.gdgbremen.mapsapibattle.library.AbstractMainActivity;
import de.gdgbremen.mapsapibattle.library.MapType;

public class MainActivity extends AbstractMainActivity implements ControlActions{

	private MyMapFragment fragment;
	
	private ControlsFragment additionalControls;


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
		
		if (getSupportFragmentManager().findFragmentByTag("controlFragment") != null) {
			additionalControls = (ControlsFragment) getSupportFragmentManager()
					.findFragmentByTag("controlFragment");
		}
		
		transaction.commit();
	}

	@Override
	protected void showAbout() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showMarker() {
		fragment.marker(this.bremenLandmarks());
	}

	@Override
	protected void hideMarker() {
		fragment.marker(null);
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

	@Override
	protected void setMapType(MapType mapType) {
		switch(mapType){
		case HYBRID:
			fragment.changeMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case ROADMAP:
			fragment.changeMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case SATELLITE:
			fragment.changeMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case TERRAIN:
			fragment.changeMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case NONE:
			fragment.changeMapType(GoogleMap.MAP_TYPE_NONE);
			break;
		}
		
	}
	
	// ####### ADDITIONAL CONTROLS

	@Override
	public void show() {
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		if (getSupportFragmentManager().findFragmentByTag("controlFragment") == null) {
			additionalControls = new ControlsFragment();
			transaction.add(R.id.singlefragment, additionalControls, "controlFragment");
		}
		
		transaction.commit();
	}

	@Override
	public void hide() {
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		if (getSupportFragmentManager().findFragmentByTag("controlFragment") != null) {
			transaction.remove(additionalControls);
		}
		transaction.commit();
	}

	@Override
	public void tiltUp() {
		fragment.tiltUp();
	}

	@Override
	public void tiltDown() {
		fragment.tiltDown();
	}

	@Override
	public void animateToNewYork() {
		fragment.navigate(City.NEW_YORK);
	}
	
	@Override
	public void animateToBremen() {
		fragment.navigate(City.BREMEN);
	}
	
	@Override
	public void animateToMunich() {
		fragment.navigate(City.MUNICH);
	}

	@Override
	public void traffic(boolean show) {
		fragment.traffic(show);
	}
}