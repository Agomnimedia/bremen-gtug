package de.gdgbremen.mapsapibattle.android;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import de.gdgbremen.mapsapibattle.library.AbstractMainActivity;

public class MainActivity extends AbstractMainActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			final FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			final MainFragment fragment = new MainFragment();
			transaction.add(R.id.singlefragment, fragment);
			transaction.commit();
		}
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
