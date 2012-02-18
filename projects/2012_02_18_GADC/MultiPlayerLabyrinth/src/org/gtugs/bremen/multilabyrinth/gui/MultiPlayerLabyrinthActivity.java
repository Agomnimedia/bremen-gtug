package org.gtugs.bremen.multilabyrinth.gui;

import org.gtugs.bremen.multilabyrinth.gui.R;
import org.gtugs.bremen.multilabyrinth.gui.R.layout;
import org.gtugs.bremen.multilabyrinth.menu.GameMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

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
        setContentView(R.layout.main);
    }
    
    
}