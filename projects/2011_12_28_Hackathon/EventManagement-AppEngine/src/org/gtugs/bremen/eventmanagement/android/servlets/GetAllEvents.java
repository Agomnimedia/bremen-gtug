package org.gtugs.bremen.eventmanagement.android.servlets;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;




@SuppressWarnings("serial")
public class GetAllEvents extends HttpServlet{

	protected void doGet(javax.servlet.http.HttpServletRequest req,	javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
				
		JSONObject ev = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		PersistEvent persistEvent = new PersistEvent();
		
		
		List<Event> events = persistEvent.getAllEvents();
		
		
		for (Event event : events){
			String name = event.getName();
			Key id = event.getPkId();
			String keys = KeyFactory.keyToString(id);

			JSONObject obj = new JSONObject();
			try {
				obj.put("name", name);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				obj.put("key", keys);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			jsonArray.put(obj);
			
		}
		
		try {
			ev.put("events", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		resp.setContentType("text/plain");
		resp.getWriter().append(ev.toString());
		
	}

}
