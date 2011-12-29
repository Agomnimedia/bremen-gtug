package org.gtugs.bremen.eventmanagement.gui;

import java.util.ArrayList;

import org.gtugs.bremen.eventmanagement.android.Communication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EventListFragment extends Fragment{

	private String servletName = "bla";
	private String title = "bla";
	
	
	/**
	 * connection to the results ListView.
	 */
	private ArrayAdapter<String> resultsAdapter;
	
	private static ArrayList<String> resultList = new ArrayList<String>();
	
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View rootView = inflater.inflate(R.layout.event_list, container, false);
		
		final TextView titleView = (TextView) rootView.findViewById(R.id.titleView);
		titleView.setText(this.title);
		
		resultsAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1);
		
		final ListView listView = (ListView) rootView.findViewById(R.id.eventListView);
		listView.setAdapter(resultsAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemClick(parent, view, position, id);
			}
				
		});
		
		final Communication comm = new AllEventsCommunication();
		comm.servlet = this.servletName;
		comm.execute();
		
		return rootView;
	}
	
	protected void onItemClick(AdapterView<?> parent, View view, int position, long id) { };
	
	//		Intent intent = new Intent(AllEventsActivity.this,
//		EventDetailActivity.class);
//// TODO putExtra Info
//startActivity(intent);

	
	
	private class AllEventsCommunication extends Communication {
		
		@Override
		protected void onPostExecute(final JSONObject result) {
			try {
				final JSONArray results = result
							.getJSONArray("events");
				resultsAdapter.clear();
				resultList.clear();
				for (int i = 0; i < results.length(); i++) {
					final JSONObject event = results.getJSONObject(i);
					resultsAdapter.add(event.getString("name"));
					resultList.add(event.getString("key"));
				}
				resultsAdapter.notifyDataSetChanged();	
			} catch (JSONException e) {
				// nothing to implement yet
			}
		}
	}
}