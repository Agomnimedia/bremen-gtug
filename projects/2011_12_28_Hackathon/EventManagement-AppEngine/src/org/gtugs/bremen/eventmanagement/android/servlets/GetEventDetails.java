package org.gtugs.bremen.eventmanagement.android.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.data.Event;
import org.gtugs.bremen.eventmanagement.android.out.DataAmount;
import org.gtugs.bremen.eventmanagement.android.out.JSONRenderer;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
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
		
		try{
			Event event = pEvent.getEventDetails(eventKey);
		
			if(event != null){
					resp.setContentType("text/plain");
					final JSONObject obj = new JSONObject();
					obj.put("event", new JSONObject(new JSONRenderer().renderEvent(event, DataAmount.COMPLETE)));
					resp.getWriter().append(obj.toString());
			} else {
				resp.setContentType("text/plain");
				// TODO return error in json
				//resp.getWriter().append("nothing found");
			}
		} catch (IllegalArgumentException e){
					// TODO return error in json
//					json.append("error", "unknown key");
//					resp.getWriter().append(json.toString());
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch(JSONException e2){
			// TODO report exception
		}
	}
}
