/**
 * 
 */
package org.gtugs.bremen.multilabyrinth.gui.fragment;

import org.gtugs.bremen.multilabyrinth.gui.R;
import org.gtugs.bremen.multilabyrinth.gui.dialog.StartGameDialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author peacei
 *
 */
public class MainFragment extends Fragment {
	
	private final String START_GAME_DIALOG = "choosetypedialog";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment, container, false);
		
		
		Button but = (Button) viewGroup.findViewById(R.id.startBut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog();	
			}
		});
		
		but = (Button) viewGroup.findViewById(R.id.optionsBut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO implement onClick	
			}
		});
		
		but = (Button) viewGroup.findViewById(R.id.aboutBut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO implement onClick	
			}
		});
		
		return viewGroup; 
	}
	
	void showDialog() {

	    // DialogFragment.show() will take care of adding the fragment
	    // in a transaction.  We also want to remove any currently showing
	    // dialog, so make our own transaction and take care of that here.
	    FragmentTransaction ft = this.getFragmentManager().beginTransaction();
	    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);

	    // Create and show the dialog.
	    DialogFragment newFragment = StartGameDialog.newInstance();
	    newFragment.show(ft, START_GAME_DIALOG);
	}
}
