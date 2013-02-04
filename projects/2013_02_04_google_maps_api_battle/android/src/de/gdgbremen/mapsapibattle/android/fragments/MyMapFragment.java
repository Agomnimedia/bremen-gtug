package de.gdgbremen.mapsapibattle.android.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

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

import de.gdgbremen.mapsapibattle.android.R;
import de.gdgbremen.mapsapibattle.library.Landmark;

public class MyMapFragment extends SupportMapFragment implements MenuActions,
		LocationSource, LocationListener {

	private GoogleMap map;

	private LocationManager locationManager;

	public static MyMapFragment newInstance() {
		final MyMapFragment fragment = new MyMapFragment();

		final GoogleMapOptions options = new GoogleMapOptions();

		options.mapType(GoogleMap.MAP_TYPE_HYBRID);

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
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
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
		this.marker(null);
		this.overlays(false);
		this.position(false);
	}

	private void showStartPosition() {
		this.navigate(City.BREMEN);
		map.setInfoWindowAdapter(null);

		map.getUiSettings().setTiltGesturesEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);

		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(final LatLng latlng) {
				final FragmentManager fm = MyMapFragment.this.getActivity()
						.getSupportFragmentManager();
				final SecretDialogFragment dialog = new SecretDialogFragment();
				dialog.show(fm, "fragment_secret_dialog");
			}
		});
	}

	public void traffic(boolean show) {
		map.setTrafficEnabled(show);
	}

	public void navigate(final City city) {
		final CameraPosition.Builder positionBuilder = new CameraPosition.Builder(
				map.getCameraPosition());
		switch (city) {
		case BREMEN:
			float zoom = 17.0f;
			if (zoom > map.getMaxZoomLevel()) {
				zoom = map.getMaxZoomLevel();
			}
			positionBuilder.zoom(zoom).target(new LatLng(53.075858, 8.80772));
			map.setIndoorEnabled(false);
			break;
		case MUNICH:
			// show indoor maps
			positionBuilder.target(new LatLng(48.129933, 11.58345));
			map.setIndoorEnabled(true);
			break;
		case NEW_YORK:
			// TODO show 3d buildings
			this.changeMapType(GoogleMap.MAP_TYPE_NORMAL);
			// TODO create tilt with specific order
			// TODO position und bearing anpassen
			positionBuilder.target(new LatLng(40.704664, -74.008235));
			map.setIndoorEnabled(false);
			break;
		default:
			break;
		}
		map.animateCamera(CameraUpdateFactory.newCameraPosition(positionBuilder
				.build()));
	}

	// ####### MAPTYPE

	@Override
	public void changeMapType(int mapType) {
		map.setMapType(mapType);
	}

	// ####### TILTING

	public void tiltUp() {
		final CameraPosition oldPosition = map.getCameraPosition();
		if (oldPosition.tilt < 90) {
			float dif = 15;
			if ((90 - oldPosition.tilt) < 15) {
				dif = 90 - oldPosition.tilt;
			}
			final CameraPosition position = new CameraPosition.Builder(
					oldPosition).tilt(oldPosition.tilt + dif).build();
			map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
		}

	}

	public void tiltDown() {
		final CameraPosition oldPosition = map.getCameraPosition();
		if (oldPosition.tilt > 1) {
			float dif = 15;
			if (oldPosition.tilt < 15) {
				dif = oldPosition.tilt;
			}
			final CameraPosition position = new CameraPosition.Builder(
					oldPosition).tilt(oldPosition.tilt - dif).build();

			map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
		}
	}

	// ####### MARKER

	private List<Marker> markerList;

	@Override
	public void marker(List<Landmark> landmarks) {
		if (landmarks == null) {
			if (markerList != null) {
				for (final Marker marker : markerList) {
					marker.remove();
				}
				markerList.clear();
			}

			map.setInfoWindowAdapter(null);
		} else {
			markerList = new ArrayList<Marker>();
			final Geocoder coder = new Geocoder(this.getActivity());
			for (final Landmark landmark : landmarks) {
				final LoadLocationTask task = new LoadLocationTask(coder);
				task.execute(landmark);
			}
			map.setInfoWindowAdapter(new AdditionalInfoWindowAdapter());
		}
	}

	private class LoadLocationTask extends
			AsyncTask<Landmark, Integer, List<MarkerOptions>> {

		private Geocoder coder;

		public LoadLocationTask(Geocoder coder) {
			this.coder = coder;
		}

		@Override
		protected List<MarkerOptions> doInBackground(Landmark... landmarks) {
			final List<MarkerOptions> resultList = new ArrayList<MarkerOptions>();
			try {
				for (final Landmark landmark : landmarks) {
					final String addressString = landmark.address
							+ ", Bremen, Germany";
					final List<Address> addresses = coder.getFromLocationName(
							addressString, 1);
					if (addresses != null && addresses.size() > 0) {
						final Address address = addresses.get(0);
						final MarkerOptions options = new MarkerOptions()
								.position(
										new LatLng(address.getLatitude(),
												address.getLongitude())).title(
										landmark.name);
						if (landmark.additionalInformation != null) {
							options.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
							options.snippet(landmark.additionalInformation
									.toString());
						} else {
							options.snippet(landmark.address);
						}
						resultList.add(options);
					} else {
						Log.w(LoadLocationTask.class.getSimpleName(),
								"No location found for landmark with title: "
										+ addressString);
					}
				}
			} catch (IOException e) {
				Log.e(LoadLocationTask.class.getSimpleName(),
						"IOException occured: " + e.getMessage());
			}
			return resultList;
		}

		@Override
		protected void onPostExecute(List<MarkerOptions> result) {
			for (final MarkerOptions options : result) {
				final Marker marker = map.addMarker(options);
				markerList.add(marker);
			}
		}

	}

	private class AdditionalInfoWindowAdapter implements InfoWindowAdapter {

		private View content;

		// private final View mWindow;

		public AdditionalInfoWindowAdapter() {
			content = getActivity().getLayoutInflater().inflate(
					R.layout.info_content_layout, null);
			// mWindow = getActivity().getLayoutInflater().inflate(
			// R.layout.info_window_layout, null);
		}

		// will be called second, after getInfoWindow returns null
		@Override
		public View getInfoContents(final Marker marker) {
			content = getActivity().getLayoutInflater().inflate(
					R.layout.info_content_layout, null);
			final TextView title = (TextView) content.findViewById(R.id.content_title);
			title.setText(marker.getTitle());
			try {
				final JSONObject json = new JSONObject(marker.getSnippet());
				if(json.has("adresse")){
					final TextView address = (TextView) content.findViewById(R.id.content_address);
					address.setText(Html.fromHtml(json.getString("adresse")));
					address.setVisibility(TextView.VISIBLE);
				}
				if(json.has("beschreibung")){
					final TextView description = (TextView) content.findViewById(R.id.content_description);
					description.setText(Html.fromHtml(json.getString("beschreibung"))/*.replaceAll("<br />", "\n").replaceAll("<p>", "").replaceAll("</p>", "")*/);
					description.setVisibility(TextView.VISIBLE);
				}
				if(json.has("homepage")){
					final TextView websiteTitle = (TextView) content.findViewById(R.id.content_website_title);
					final TextView website = (TextView) content.findViewById(R.id.content_website);
					website.setText(Html.fromHtml(json.getString("homepage")));
					websiteTitle.setVisibility(TextView.VISIBLE);
					website.setVisibility(TextView.VISIBLE);
				}
				if(json.has("oeffnungszeiten")){
					final TextView oeffnungszeitenTitle = (TextView) content.findViewById(R.id.content_oeffnungszeiten_title);
					final TextView oeffnungszeiten = (TextView) content.findViewById(R.id.content_oeffnungszeiten);
					oeffnungszeiten.setText(Html.fromHtml(json.getString("oeffnungszeiten")));
					oeffnungszeitenTitle.setVisibility(TextView.VISIBLE);
					oeffnungszeiten.setVisibility(TextView.VISIBLE);
				}
				
				return content;
			} catch (JSONException e) {
				return null;
			}
		}

		// will be called first
		@Override
		public View getInfoWindow(final Marker marker) {
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
					.transparency(0.3f));

		} else {
			if (groundOverlay != null) {
				if (groundOverlay != null) {
					groundOverlay.remove();
				}
			}
		}
	}

	// ####### POSITION

	@Override
	public void position(boolean show) {
		if (show) {
			if (locationManager != null) {
				map.setMyLocationEnabled(true);
			}
		} else {
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
	public void activate(OnLocationChangedListener listener) {
	}

	@Override
	public void deactivate() {
	}

	// Interface methods LocationListener

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}