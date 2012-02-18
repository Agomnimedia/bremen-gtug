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
    
    private void startGame(final GameMode mode){
    	final Intent intent = new Intent();
    	switch(mode){
    	case SINGLE:
    		intent.setClass(getApplicationContext(), SingleGameActivity.class);
    		// TODO not implemented yet
    		break;
    	case MULTI_CHALLENGE:
    		// TODO not implemented yet
    		break;
    	case MULTI_COOP:
    		intent.setClass(getApplicationContext(), MultiCoopGameActivity.class);
    		// TODO set player amount
    		// TODO set creation mode
    		// TODO set ...
    		break;
    	}
    	
    	startActivity(intent);
    }
    
    public void onStartButClick(View view) {
    	
    }
    
    public void onOptButClick(View view) {
    	
    }
    
    public void onAboutButClick(View view) {
    	
    }
}