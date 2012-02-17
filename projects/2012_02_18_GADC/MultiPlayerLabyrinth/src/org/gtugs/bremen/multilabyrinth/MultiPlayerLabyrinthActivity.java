package org.gtugs.bremen.multilabyrinth;

import org.gtugs.bremen.multilabyrinth.menu.GameMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This is the start activity of the project. Show the menu here
 * 
 * @author Bremen GTUG
 */
public class MultiPlayerLabyrinthActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        this.startGame(GameMode.MULTI_COOP);
    }
    
    private void startGame(final GameMode mode){
    	final Intent intent = new Intent();
    	switch(mode){
    	case SINGLE:
    		// TODO not implemented yet
    		break;
    	case MULTI_CHALLENGE:
    		// TODO not implemented yet
    		break;
    	case MULTI_COOP:
    		intent.setClass(getApplicationContext(), GameActivity.class);
    		// TODO set player amount
    		// TODO set creation mode
    		// TODO set ...
    		break;
    	}
    	
    	startActivity(intent);
    }
}