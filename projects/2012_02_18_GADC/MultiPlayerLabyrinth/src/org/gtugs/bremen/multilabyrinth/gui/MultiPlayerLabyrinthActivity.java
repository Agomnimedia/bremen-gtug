package org.gtugs.bremen.multilabyrinth.gui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.gtugs.bremen.multilabyrinth.gui.fragment.MainFragment;
import org.gtugs.bremen.multilabyrinth.gui.fragment.NetworkSearchingFragment;

/**
 * This is the start activity of the project. Show the menu here
 * 
 * @author Bremen GTUG
 */
public class MultiPlayerLabyrinthActivity extends FragmentActivity {
	
	public static final String MODE_EXTRA_NAME = "mode";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.singlefragment_activity);
        
        final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		MainFragment fragment = new MainFragment();
		transaction.add(R.id.singlefragment, fragment);
		transaction.commit();
    }
    
    
}