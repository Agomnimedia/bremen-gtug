package de.gdgbremen.mapsapibattle.library;

import java.util.List;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class AbstractMainActivity extends FragmentActivity {

	protected abstract void showAbout();

	protected abstract void showMarker();

	protected abstract void hideMarker();

	protected abstract void showOverlays();

	protected abstract void hideOverlays();

	protected abstract void showPosition();

	protected abstract void hidePosition();

	protected abstract void showRoute();

	protected abstract void hideRoute();

	protected abstract void setMapType(MapType mapType);

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	protected List<Landmark> bremenLandmarks(){
		final AssetManager assetManager = getAssets();
		final XMLLandmarkParser parser = new XMLLandmarkParser(assetManager,"data.xml");
		return parser.parseLandmarks();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int itemId = item.getItemId();
		if (itemId == R.id.menu_about) {
			this.showAbout();
			return true;
		} else if (itemId == R.id.menu_marker) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.hideMarker();
			} else {
				item.setChecked(true);
				this.showMarker();
			}
			return true;
		} else if (itemId == R.id.menu_overlays) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.hideOverlays();
			} else {
				item.setChecked(true);
				this.showOverlays();
			}
			return true;
		} else if (itemId == R.id.menu_position) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.hidePosition();
			} else {
				item.setChecked(true);
				this.showPosition();
			}
			return true;
		} else if (itemId == R.id.menu_route) {
			if (item.isChecked()) {
				item.setChecked(false);
				this.hideRoute();
			} else {
				item.setChecked(true);
				this.showRoute();
			}
			return true;
		} else if (itemId == R.id.menu_maptype) {
			// nothing to do, opens only the submenu
			return true;
		} else if (itemId == R.id.menu_maptype_roadmap) {
			setMapType(MapType.ROADMAP);
			return true;
		} else if (itemId == R.id.menu_maptype_terrain) {
			setMapType(MapType.TERRAIN);
			return true;
		} else if (itemId == R.id.menu_maptype_satellite) {
			setMapType(MapType.SATELLITE);
			return true;
		} else if (itemId == R.id.menu_maptype_hybrid) {
			setMapType(MapType.HYBRID);
			return true;
		} else if (itemId == R.id.menu_maptype_none) {
			setMapType(MapType.NONE);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

}
