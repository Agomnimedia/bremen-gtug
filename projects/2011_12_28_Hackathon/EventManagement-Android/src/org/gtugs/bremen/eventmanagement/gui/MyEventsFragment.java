/**
 * 
 */
package org.gtugs.bremen.eventmanagement.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @author peacei
 *
 */
public class MyEventsFragment extends EventListFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.setServletName("getmyevents");
		this.setTitle(this.getResources().getString(R.string.myEventsTitle));
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	protected void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}
}
