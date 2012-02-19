/**
 * 
 */
package org.gtugs.bremen.multilabyrinth.gui.dialog;

import org.gtugs.bremen.multilabyrinth.gui.MultiCoopGameActivity;
import org.gtugs.bremen.multilabyrinth.gui.MultiPlayerLabyrinthActivity;
import org.gtugs.bremen.multilabyrinth.gui.NetworkSearchingActivity;
import org.gtugs.bremen.multilabyrinth.gui.R;
import org.gtugs.bremen.multilabyrinth.gui.SingleGameActivity;
import org.gtugs.bremen.multilabyrinth.menu.GameMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 * @author peacei
 *
 */
public class StartGameDialog extends DialogFragment {
	
	private Spinner levelSpinner, typeSpinner;
	private CheckBox serverCheckBox;
	
	public static StartGameDialog newInstance() {
		return new StartGameDialog();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.startgame_dialog, container, false);
		
		this.getDialog().setTitle(R.string.start_game_title);
		
		levelSpinner = (Spinner) viewGroup.findViewById(R.id.level_spinner);
		typeSpinner = (Spinner) viewGroup.findViewById(R.id.type_spinner);
		serverCheckBox = (CheckBox) viewGroup.findViewById(R.id.server_check);
		serverCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				levelSpinner.setEnabled(serverCheckBox.isChecked());
			}
			
		});
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this.getActivity().getApplicationContext(), R.array.types, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    typeSpinner.setAdapter(adapter);
	    
	    
	    
	    typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
            	switch(position) {
            		case 0:	
            			ArrayAdapter<CharSequence> singleAdapter = ArrayAdapter.createFromResource(
            		            StartGameDialog.this.getActivity().getApplicationContext(), R.array.single_levels, android.R.layout.simple_spinner_item);
            		    singleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            		    levelSpinner.setAdapter(singleAdapter);
            		    serverCheckBox.setEnabled(false);
            		    serverCheckBox.setChecked(false);
            			break;
            		case 1: case 2: default:
            			ArrayAdapter<CharSequence> multiAdapter = ArrayAdapter.createFromResource(
            		            StartGameDialog.this.getActivity().getApplicationContext(), R.array.multi_levels, android.R.layout.simple_spinner_item);
            		    multiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            		    levelSpinner.setAdapter(multiAdapter);
            		    
            		    serverCheckBox.setEnabled(true);
            		    levelSpinner.setEnabled(false);
            	}
            }
            public void onNothingSelected(AdapterView<?> parent) { }
	    });
	    
	    Button button = (Button) viewGroup.findViewById(R.id.startBut);
	    button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int position = typeSpinner.getSelectedItemPosition();
				
				switch(position) {
					case 0:
						startGame(GameMode.SINGLE, levelSpinner.getSelectedItemPosition()+1);
						break;
					case 1:  
						startGame(GameMode.MULTI_CHALLENGE, levelSpinner.getSelectedItemPosition()+11);
						break;
					case 2:
						startGame(GameMode.MULTI_COOP, levelSpinner.getSelectedItemPosition()+11);
						break;
					default:
				}
			}
		});
	    
	    button = (Button) viewGroup.findViewById(R.id.cancelBut);
	    button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	    
		return viewGroup; 
	}
	
	private void startGame(final GameMode mode, final int level){
    	final Intent intent = new Intent();
    	
    	switch(mode) {
    	case SINGLE:
    		intent.setClass(this.getActivity().getApplicationContext(), SingleGameActivity.class);
    		intent.putExtra(MultiPlayerLabyrinthActivity.MODE_EXTRA_NAME, level);
    		// TODO not implemented yet
    		break;
    	case MULTI_CHALLENGE:
    		// TODO not implemented yet
    		break;
    	case MULTI_COOP:
    		 
    		if(serverCheckBox.isChecked()) {
    			intent.setClass(this.getActivity().getApplicationContext(), MultiCoopGameActivity.class);
    			intent.putExtra(MultiPlayerLabyrinthActivity.MODE_EXTRA_NAME, level);
    		} else {
    			intent.setClass(this.getActivity().getApplicationContext(), NetworkSearchingActivity.class);
    		}
    		break;
    	}
    	
    	startActivity(intent);
    	
    	this.dismiss();
    }
}
