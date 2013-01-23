package de.gdgbremen.mapsapibattle.android;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.gdgbremen.mapsapibattle.library.AbstractMainActivity;

public class MainActivity extends AbstractMainActivity {

	private SupportMapFragment fragment;

	private GoogleMap map;
	
	private List<Marker> markerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		final GoogleMapOptions options = new GoogleMapOptions();

		options.mapType(GoogleMap.MAP_TYPE_HYBRID);
		
		// TODO das in setting oder so angeben lassen?
		// options.zoomControlsEnabled(true);
		// options.zoomGesturesEnabled(true);
		// options.compassEnabled(true);
		
		// TODO uisettings ausprobieren
		
		
		if(getSupportFragmentManager().findFragmentByTag("mapFragment") == null){
			fragment = SupportMapFragment.newInstance(options);
			transaction.add(R.id.singlefragment, fragment, "mapFragment");
		}else{
			fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("mapFragment");
		}
		transaction.commit();
		
		if (savedInstanceState == null) {
		
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		map = fragment.getMap();
		showTest();
	}

	private void showTest() {
		float zoom = 17.0f;
		if (zoom > map.getMaxZoomLevel()) {
			zoom = map.getMaxZoomLevel();
		}
		// TODO auswählbar in eigenen dialog o.ä
		// map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		// map.setMapType(GoogleMap.MAP_TYPE_NONE);
		// map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		final CameraPosition position = new CameraPosition.Builder().zoom(zoom)
				.target(new LatLng(53.075858, 8.80772)).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
		map.setInfoWindowAdapter(null);
	}

	@Override
	protected void showAbout() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void showMarker() {
		// TODO Auto-generated method stub
		markerList = new ArrayList<Marker>();
		
		final Marker domMarker = 
				map.addMarker(
						new MarkerOptions()
			        		.position(new LatLng(53.075439,8.808761))
			        		.title("Das ist unser wunderschöner Dom :-)")
			        		.snippet("Übrigens wunderschön bei Nacht"));
		
		markerList.add(domMarker);
		
		final Marker rolandMarker =
				map.addMarker(
						new MarkerOptions()
							.position(new LatLng(53.075853,8.807237))
							.title("Roland")
							.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
		
		markerList.add(rolandMarker);
		
		final Marker rathausMarker =
				map.addMarker(new MarkerOptions()
				.position(new LatLng(53.07596,8.807556))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.bremer_wappen_smaller)));
		
		markerList.add(rathausMarker);
		
		map.setInfoWindowAdapter(new TestInfoWindowAdapter());
	}

	@Override
	protected void hideMarker() {
		// TODO Auto-generated method stub
		for(final Marker marker : markerList){
			marker.remove();
		}
		markerList.clear();
		
		map.setInfoWindowAdapter(null);
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
	
	private class TestInfoWindowAdapter implements InfoWindowAdapter{

		private final View mContent;
		
		private final View mWindow;
		
		public TestInfoWindowAdapter() {
			mContent = getLayoutInflater().inflate(R.layout.info_content_layout, null);
			mWindow = getLayoutInflater().inflate(R.layout.info_window_layout, null);
		}
		
		@Override
		public View getInfoContents(final Marker marker) {
			if(marker.getTitle()!=null && marker.getSnippet() == null){
				return mContent;
			}
			return null;
		}

		@Override
		public View getInfoWindow(final Marker marker) {
			if(marker.getTitle()==null && marker.getSnippet() == null){
				return mWindow;
			}
			return null;
		}

		
	}

}
