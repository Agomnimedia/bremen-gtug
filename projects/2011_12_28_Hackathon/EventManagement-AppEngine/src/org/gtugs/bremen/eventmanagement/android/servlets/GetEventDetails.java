package org.gtugs.bremen.eventmanagement.android.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class GetEventDetails extends HttpServlet {

	
	protected void doGet(javax.servlet.http.HttpServletRequest req, 
			javax.servlet.http.HttpServletResponse resp)
					throws javax.servlet.ServletException, 
					java.io.IOException {
	
		PersistEvent pEvent = new PersistEvent();

		
		String key = (String) req.getParameter("key");
		
		final Key eventKey = KeyFactory.stringToKey(key);
		
		JSONObject json = new JSONObject();
		try{
			Event event = pEvent.getEventDetails(eventKey);
		
		
			if(event != null){
			
				try {
					json.append("key", event.getPkId());
					json.append("name", event.getName());
					json.append("location", event.getLocation());
					json.append("startDate", event.getStartDate());
					json.append("endDate", event.getEndDate());
					json.append("url", event.getUrl());
					json.append("attendees", event.getAttendies());
					json.append("admin", event.getAdmin());
					json.append("talks", event.getTalks());
					json.append("maxAttendies", event.getMaxAttendees());
					resp.setContentType("text/plain");
					resp.getWriter().append(json.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				resp.setContentType("text/plain");
				resp.getWriter().append("nothing found");
			}
		} catch (IllegalArgumentException e){
				try {
					json.append("error", "unknown key");
					resp.getWriter().append(json.toString());
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		}
	}
}
