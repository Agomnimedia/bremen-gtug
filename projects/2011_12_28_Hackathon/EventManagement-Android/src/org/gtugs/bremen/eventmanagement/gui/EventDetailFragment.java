package org.gtugs.bremen.eventmanagement.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class EventDetailFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View rootView = inflater.inflate(R.layout.eventdetails, container);
		
		final TextView titleView = (TextView) rootView.findViewById(R.id.titleView);
		//titleView.setText(this.title);
		
		final ListView listView = (ListView) rootView.findViewById(R.id.eventListView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemClick(parent, view, position, id);
			}
				
		});
		
		return rootView;
	}
	
}
