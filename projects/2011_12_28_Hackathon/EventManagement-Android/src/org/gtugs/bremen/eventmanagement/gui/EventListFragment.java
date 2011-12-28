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

public class EventListFragment extends Fragment{

	/**
	 * connection to the results ListView.
	 */
	private ArrayAdapter<String> resultsAdapter;
	
	/**
	 * list of customer ids that listed in the results.
	 */
	private static ArrayList<String> resultList = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View rootView = inflater.inflate(R.layout.allevents, container);
		
		final ListView listView = (ListView) rootView.findViewById(R.id.allEventsView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent intent = new Intent(AllEventsActivity.this,
//						EventDetailActivity.class);
//				// TODO putExtra Info
//				startActivity(intent);
			}
			
		});
		
		final Communication comm = new AllEventsCommunication();
		comm.servlet = "getallevents";
		comm.execute();
		return rootView;
	}
	
	private class AllEventsCommunication extends Communication{
		
		@Override
		protected void onPostExecute(final JSONObject result) {
			try {
				final JSONArray results = result
							.getJSONArray("events");
				resultsAdapter.clear();
				resultList.clear();
				for (int i = 0; i < results.length(); i++) {
					final JSONObject event = results.getJSONObject(i);
					resultsAdapter.add(event.getString("key"));
					resultList.add(event.getString("name"));
				}
				resultsAdapter.notifyDataSetChanged();	
			} catch (JSONException e) {
				// nothing to implement yet
			}
		}
	}
}