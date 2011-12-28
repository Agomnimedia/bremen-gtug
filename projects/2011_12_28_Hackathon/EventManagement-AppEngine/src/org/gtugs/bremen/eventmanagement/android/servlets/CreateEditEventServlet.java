package org.gtugs.bremen.eventmanagement.android.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.KeyFactory;

public class CreateEditEventServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 137249123517624948L;

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		
//	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String jsonValue = req.getParameter("????");
		
		try {
			if (jsonValue != null && !jsonValue.isEmpty()) {
		        final JSONObject object = new JSONObject(jsonValue);
				if(object != null){
					final Event event = this.parseJSON2Event(object);
					new PersistEvent().insert(event);
					// TODO return 'successfull' response
				}else{
					// TODO return 'error' response
				}
			}else{
				// TODO return 'error' response
			}
		} catch (JSONException e) {
			// TODO return 'error' response
		}
	}
	
	private Event parseJSON2Event(final JSONObject object) {
		Event event = new Event();
		try{
		event.setPkId(KeyFactory.stringToKey(object.getString("key")));
		event.setName(object.getString("name"));
		event.setLocation(object.getString("location"));
		event.setStartDate(new Date(Long.parseLong(object.getString("startDate"))));
		event.setEndDate(new Date(Long.parseLong(object.getString("endDate"))));
		event.setUrl(object.getString("url"));
		// TODO get admin by 
//		event.setAdmin(object.getString("admin"));
		event.setMaxAttendees(Integer.parseInt(object.getString("maxAttendees")));
		}catch(JSONException je){
			event = null;
			// TODO handle exception
		}
		return event;
	}
}
