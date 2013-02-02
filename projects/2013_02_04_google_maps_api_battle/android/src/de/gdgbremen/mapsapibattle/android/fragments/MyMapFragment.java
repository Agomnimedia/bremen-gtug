package de.gdgbremen.mapsapibattle.android.fragments;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.gdgbremen.mapsapibattle.android.ControlActions;
import de.gdgbremen.mapsapibattle.android.R;

public class MyMapFragment extends SupportMapFragment implements MenuActions, 
		LocationSource, LocationListener {

	private GoogleMap map;

	private LocationManager locationManager;

	public static MyMapFragment newInstance() {
		final MyMapFragment fragment = new MyMapFragment();

		final GoogleMapOptions options = new GoogleMapOptions();

		options.mapType(GoogleMap.MAP_TYPE_HYBRID);
		// TODO uisettings ausprobieren

		Bundle args = new Bundle();
		args.putParcelable("MapOptions", options); // obtained by decompiling
													// google-play-services.jar
		fragment.setArguments(args);

		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		final View view = super.onCreateView(arg0, arg1, arg2);
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, 1.0f);
		view.setLayoutParams(param);
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLocationManager();
	}

	@Override
	public void onStart() {
		super.onStart();
		map = super.getMap();
		showStartPosition();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		this.marker(false);
		this.overlays(false);
		this.position(false);
	}

	private void showStartPosition() {
		float zoom = 17.0f;
		if (zoom > map.getMaxZoomLevel()) {
			zoom = map.getMaxZoomLevel();
		}

		final LatLng bremenCity = new LatLng(53.075858, 8.80772);
		final CameraPosition position = new CameraPosition.Builder().zoom(zoom)
				.target(bremenCity).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
		map.setInfoWindowAdapter(null);
//		map.setIndoorEnabled(arg0)
//		map.setTrafficEnabled(arg0)

		map.getUiSettings().setTiltGesturesEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);

		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(final LatLng latlng) {
				final FragmentManager fm = MyMapFragment.this.getActivity().getSupportFragmentManager();
		        final SecretDialogFragment dialog = new SecretDialogFragment();
		        dialog.show(fm, "fragment_secret_dialog");
			}
		});
	}
	
	// ####### MAPTYPE
	
	@Override
	public void changeMapType(int mapType) {
		map.setMapType(mapType);
	}
	
	// ####### TILTING
	
	public void tiltUp() {
		final CameraPosition oldPosition = map.getCameraPosition();
		if(oldPosition.tilt<90){
			float dif = 15;
			if((90-oldPosition.tilt) < 15){
				dif = 90-oldPosition.tilt;
			}
			final CameraPosition position = new CameraPosition.Builder(oldPosition)
					.tilt(oldPosition.tilt+dif).build();
			map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
		}
		
	}

	public void tiltDown() {
		final CameraPosition oldPosition = map.getCameraPosition();
		if(oldPosition.tilt>1){
			float dif = 15;
			if(oldPosition.tilt < 15){
				dif = oldPosition.tilt;
			}
			final CameraPosition position = new CameraPosition.Builder(oldPosition)
					.tilt(oldPosition.tilt-dif).build();
			
			map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
		}
	}

	// ####### MARKER

	private List<Marker> markerList;

	@Override
	public void marker(boolean show) {
		if (show) {
			markerList = new ArrayList<Marker>();

			final Marker domMarker = map.addMarker(new MarkerOptions()
					.position(new LatLng(53.075439, 8.808761))
					.title("Das ist unser wunderschöner Dom :-)")
					.snippet("Übrigens wunderschön bei Nacht"));

			markerList.add(domMarker);

			final Marker rolandMarker = map
					.addMarker(new MarkerOptions()
							.position(new LatLng(53.075853, 8.807237))
							.title("Roland")
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

			markerList.add(rolandMarker);

			final Marker rathausMarker = map.addMarker(new MarkerOptions()
					.position(new LatLng(53.07596, 8.807556))
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.bremer_wappen_smaller)));

			markerList.add(rathausMarker);

			map.setInfoWindowAdapter(new TestInfoWindowAdapter());
		} else {
			if(markerList != null){
				for (final Marker marker : markerList) {
					marker.remove();
				}
				markerList.clear();
			}

			map.setInfoWindowAdapter(null);
		}
	}

	private class TestInfoWindowAdapter implements InfoWindowAdapter {

		private final View mContent;

		private final View mWindow;

		public TestInfoWindowAdapter() {
			mContent = getActivity().getLayoutInflater().inflate(
					R.layout.info_content_layout, null);
			mWindow = getActivity().getLayoutInflater().inflate(
					R.layout.info_window_layout, null);
		}

		@Override
		public View getInfoContents(final Marker marker) {
			if (marker.getTitle() != null && marker.getSnippet() == null) {
				return mContent;
			}
			return null;
		}

		@Override
		public View getInfoWindow(final Marker marker) {
			if (marker.getTitle() == null && marker.getSnippet() == null) {
				return mWindow;
			}
			return null;
		}

	}

	// ####### OVERLAYS

	private GroundOverlay groundOverlay;

	@Override
	public void overlays(boolean show) {
		if (show) {
			BitmapDescriptor image = BitmapDescriptorFactory
					.fromResource(R.drawable.historisch);
			final LatLng southwest = new LatLng(53.073409, 8.807302);
			final LatLng northeast = new LatLng(53.076516, 8.812848);
			LatLngBounds bounds = new LatLngBounds(southwest, northeast);
			groundOverlay = map.addGroundOverlay(new GroundOverlayOptions()
					.image(image).positionFromBounds(bounds).bearing(39.0f)
					.transparency(0.4f));

			markerList = new ArrayList<Marker>();

			// TODO remove this for presentation
			final Marker swMarker = map.addMarker(new MarkerOptions()
					.position(southwest));
			markerList.add(swMarker);

			final Marker neMarker = map.addMarker(new MarkerOptions()
					.position(northeast));
			markerList.add(neMarker);
		} else {
			if(groundOverlay != null){
				if (groundOverlay != null) {
					groundOverlay.remove();
				}
			}
			if(markerList != null){
				for (final Marker marker : markerList) {
					marker.remove();
				}
				markerList.clear();
			}
		}
	}

	// ####### POSITION

	@Override
	public void position(boolean show) {
		if(show){
			if (locationManager != null) {
				map.setMyLocationEnabled(true);
			}
		}else{
			if (locationManager != null) {
				map.setMyLocationEnabled(false);
				locationManager.removeUpdates(this);
			}
		}
	}
	
	private void initLocationManager() {
		locationManager = (LocationManager) getActivity().getSystemService(
				FragmentActivity.LOCATION_SERVICE);

		if (locationManager != null) {
			boolean gpsIsEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean networkIsEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (gpsIsEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 5000L, 10F, this);
			} else if (networkIsEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
			} 
		}
	}

	// Interface methods LocationSource
	
	@Override
	public void activate(OnLocationChangedListener listener) {}

	@Override
	public void deactivate() {}
	
	// Interface methods LocationListener

	@Override
	public void onLocationChanged(Location location) {}

	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	// ####### ROUTE

	@Override
	public void route(boolean show) {
		// TODO Auto-generated method stub

	}
}