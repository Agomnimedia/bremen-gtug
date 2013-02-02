package de.gdgbremen.mapsapibattle.android.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
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

import de.gdgbremen.mapsapibattle.android.R;

public class MyMapFragment extends SupportMapFragment implements MenuActions{

	private GoogleMap map;
	
	public static MyMapFragment newInstance(){
		final MyMapFragment fragment = new MyMapFragment();

		final GoogleMapOptions options = new GoogleMapOptions();

		options.mapType(GoogleMap.MAP_TYPE_HYBRID);

		// TODO das in setting oder so angeben lassen?
		// options.zoomControlsEnabled(true);
		// options.zoomGesturesEnabled(true);
		// options.compassEnabled(true);

		// TODO uisettings ausprobieren
		
        Bundle args = new Bundle();
        args.putParcelable("MapOptions", options); //obtained by decompiling google-play-services.jar
        fragment.setArguments(args);

        return fragment;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		map = super.getMap();
		showStartPosition();
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
		
		map.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(final LatLng latlng) {
				// TODO show dialogfragment
			}
		});
	}
	
	
	// ####### MARKER
	
	private List<Marker> markerList;
	
	@Override
	public void marker(boolean show) {
		if(show){
			markerList = new ArrayList<Marker>();

			final Marker domMarker = map.addMarker(new MarkerOptions()
					.position(new LatLng(53.075439, 8.808761))
					.title("Das ist unser wunderschöner Dom :-)")
					.snippet("Übrigens wunderschön bei Nacht"));

			markerList.add(domMarker);

			final Marker rolandMarker = map.addMarker(new MarkerOptions()
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
		}else{
			for (final Marker marker : markerList) {
				marker.remove();
			}
			markerList.clear();

			map.setInfoWindowAdapter(null);
		}
	}
	
	private class TestInfoWindowAdapter implements InfoWindowAdapter {

		private final View mContent;

		private final View mWindow;

		public TestInfoWindowAdapter() {
			mContent = getActivity().getLayoutInflater().inflate(
					R.layout.info_content_layout, null);
			mWindow = getActivity().getLayoutInflater().inflate(R.layout.info_window_layout,
					null);
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
		if(show){
			BitmapDescriptor image = BitmapDescriptorFactory
					.fromResource(R.drawable.historisch);
			final LatLng southwest = new LatLng(53.073409,8.807302);
			final LatLng northeast = new LatLng(53.076516,8.812848);
			 LatLngBounds bounds = new LatLngBounds(southwest, northeast);
			 groundOverlay = map.addGroundOverlay(new GroundOverlayOptions()
			      .image(image)
			      .positionFromBounds(bounds)
			      .bearing(39.0f)
			      .transparency(0.4f));
			 
			 markerList = new ArrayList<Marker>();
			 
			 // TODO remove this for presentation
			 final Marker swMarker = map.addMarker(new MarkerOptions()
				.position(southwest));
			 markerList.add(swMarker);
			 
			 final Marker neMarker = map.addMarker(new MarkerOptions()
				.position(northeast));
			 markerList.add(neMarker);
		}else{
			if(groundOverlay!=null){
				groundOverlay.remove();
			}
			for (final Marker marker : markerList) {
				marker.remove();
			}
			markerList.clear();
		}
	}
	
	// ####### POSITION

	@Override
	public void position(boolean show) {
		// TODO Auto-generated method stub
		
	}
	
	// ####### ROUTE

	@Override
	public void route(boolean show) {
		// TODO Auto-generated method stub
		
	}
}
