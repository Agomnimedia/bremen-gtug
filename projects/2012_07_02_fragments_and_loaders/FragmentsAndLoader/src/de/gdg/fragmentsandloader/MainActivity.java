package de.gdg.fragmentsandloader;


import de.gdg.fragmentsandloader.fragments.DetailsFragment;
import de.gdg.fragmentsandloader.fragments.ContactListFragment.OnContactSelectedListener;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements OnContactSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onContactSelected(int contactId) {
		DetailsFragment details = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_details);
		if(findViewById(R.id.fragment_details) != null){
			if(details == null || details.getShownIndex() != contactId){
				details = new DetailsFragment().newInstance(contactId);
				final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.fragment_details, details);
				ft.commit();
			}
		}else{
			final Intent intent = new Intent();
			intent.setClass(this, DetailsActivity.class);
			intent.putExtra(DetailsFragment.CONTACT_ID, contactId);
			startActivity(intent);
		}
	}    
}
