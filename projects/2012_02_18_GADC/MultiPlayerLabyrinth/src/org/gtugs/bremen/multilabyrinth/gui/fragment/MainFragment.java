/**
 * 
 */
package org.gtugs.bremen.multilabyrinth.gui.fragment;

import org.gtugs.bremen.multilabyrinth.gui.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author peacei
 *
 */
public class MainFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment, container, false);
		
		return viewGroup; 
	}
}
