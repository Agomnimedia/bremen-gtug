package org.gtugs.bremen.multilabyrinth.gui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * This is the start activity of the project. Show the menu here
 * 
 * @author Bremen GTUG
 */
public class MultiPlayerLabyrinthActivity extends FragmentActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
    }
    
    
}