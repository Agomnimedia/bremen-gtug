/**
 * 
 */
package org.gtugs.bremen.multilabyrinth.gui.dialog;

import org.gtugs.bremen.multilabyrinth.gui.MultiCoopGameActivity;
import org.gtugs.bremen.multilabyrinth.gui.R;
import org.gtugs.bremen.multilabyrinth.gui.SingleGameActivity;
import org.gtugs.bremen.multilabyrinth.menu.GameMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author peacei
 *
 */
public class StartGameDialog extends DialogFragment {
	
	public static StartGameDialog newInstance() {
		return new StartGameDialog();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.startgame_dialog, container, false);
		
		this.getDialog().setTitle(R.string.choose_type_text);
		
		Button but = (Button) viewGroup.findViewById(R.id.singleBut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startGame(GameMode.SINGLE);	
			}
		});
		
		but = (Button) viewGroup.findViewById(R.id.challengeBut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startGame(GameMode.MULTI_CHALLENGE);	
			}
		});
		
		but = (Button) viewGroup.findViewById(R.id.coopBut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startGame(GameMode.MULTI_COOP);	
			}
		});
		
		return viewGroup; 
	}
	
	private void startGame(final GameMode mode){
    	final Intent intent = new Intent();
    	switch(mode){
    	case SINGLE:
    		intent.setClass(this.getActivity().getApplicationContext(), SingleGameActivity.class);
    		// TODO not implemented yet
    		break;
    	case MULTI_CHALLENGE:
    		// TODO not implemented yet
    		break;
    	case MULTI_COOP:
    		intent.setClass(this.getActivity().getApplicationContext(), MultiCoopGameActivity.class);
    		// TODO set player amount
    		// TODO set creation mode
    		// TODO set ...
    		break;
    	}
    	
    	startActivity(intent);
    	
    	this.dismiss();
    }
}
