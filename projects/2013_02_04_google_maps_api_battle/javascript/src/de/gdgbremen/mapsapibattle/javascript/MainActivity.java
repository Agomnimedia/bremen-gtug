package de.gdgbremen.mapsapibattle.javascript;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import de.gdgbremen.mapsapibattle.javascript.fragments.MainFragment;
import de.gdgbremen.mapsapibattle.library.AbstractMainActivity;
import de.gdgbremen.mapsapibattle.library.Landmark;
import de.gdgbremen.mapsapibattle.library.MapType;

public class MainActivity extends AbstractMainActivity {
	private MainFragment fragment;

	private ProgressDialog geocoderProgressDialog;

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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.infolayer, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int itemId = item.getItemId();
		if (itemId == R.id.menu_infolayer) {
			// nothing to do, opens only the submenu
			return true;
		} else if (itemId == R.id.menu_infolayer_traffic) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.fragment.hideTraffic();
			} else {
				item.setChecked(true);
				this.fragment.showTraffic();
			}
			return true;
		} else if (itemId == R.id.menu_infolayer_transit) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.fragment.hideTransit();
			} else {
				item.setChecked(true);
				this.fragment.showTransit();
			}
			return true;
		} else if (itemId == R.id.menu_infolayer_bicycling) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.fragment.hideBicycling();
			} else {
				item.setChecked(true);
				this.fragment.showBicycling();
			}
			return true;
		} else if (itemId == R.id.menu_infolayer_weather) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.fragment.hideWeather();
			} else {
				item.setChecked(true);
				this.fragment.showWeather();
			}
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	public void showGeocodingProgress(int maxCount) {
		geocoderProgressDialog = new ProgressDialog(this);
		geocoderProgressDialog.setTitle(getText(R.string.geocodingLoading));
		geocoderProgressDialog.setMessage(getText(R.string.geocodingLoadingMsg));
		geocoderProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		geocoderProgressDialog.setCancelable(false);
		geocoderProgressDialog.setProgress(0);
		geocoderProgressDialog.setMax(maxCount);
		geocoderProgressDialog.show();
	}

	public void setGeocodingProgressState(int state) {
		geocoderProgressDialog.setProgress(state);
		if (geocoderProgressDialog.getMax() == state) {
			geocoderProgressDialog.dismiss();
		}
	}

	public List<Landmark> getLandmarks() {
		return bremenLandmarks();
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
	protected void setMapType(MapType mapType) {
		fragment.setMapType(mapType);
	}

}
