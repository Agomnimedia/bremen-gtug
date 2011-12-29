package org.gtugs.bremen.eventmanagement.android.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.data.Attendee;
import org.gtugs.bremen.eventmanagement.android.data.Event;
import org.gtugs.bremen.eventmanagement.android.data.Update;
import org.gtugs.bremen.eventmanagement.android.exceptions.NotAllowedException;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistAtendee;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class CreateEditEventServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 137249123517624948L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
		if(user == null){
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}else{
			String jsonValue = req.getParameter("event");
			try {
				if (jsonValue != null && !jsonValue.isEmpty()) {
			        final JSONObject object = new JSONObject(jsonValue);
					PersistEvent perEvent = new PersistEvent();
					final Event event = this.parseJSON2Event(object, user);
					perEvent.insert(event);
					final Update update = new Update();
					update.setDate(new Date());
					if(object.getString("key") == null){
						// TODO tell google servers that allevents changed
						update.setKind(Integer.valueOf(1));
						update.setAdditionalInfo(String.valueOf(perEvent.getAllEvents().size()));
					}else{
						// TODO tell google servers that event x changed
						update.setKind(Integer.valueOf(2));
						update.setAdditionalInfo(event.getName());
					}
				}else{
					resp.setStatus(501);
				}
			} catch (JSONException e) {
				resp.setStatus(500);
			} catch(NotAllowedException nae){
				resp.setStatus(400);
			}
		}
	}
	
	private Event parseJSON2Event(final JSONObject object, final User user) throws NotAllowedException{
		Event event;
		try{
			final PersistAtendee perAtt = new PersistAtendee();
			Attendee attendee = perAtt.getUser(user);
			if(attendee == null){
				attendee = new Attendee();
				attendee.setAdmin(false);
				attendee.setEmail(user.getEmail());
				attendee.setNickname(user.getNickname());
				perAtt.insert(attendee);
				throw new NotAllowedException("Unknown User is not allowed to create an event!");
			}
			final String key = object.getString("key");
			if(key == null){
				event = new Event();
				if(attendee.isAdmin()){
					event.setAdmin(attendee.getKey());
				}else{
					throw new NotAllowedException("User has no authority to create or edit an event!");
				}
			}else{
				event = new PersistEvent().getEventDetails(KeyFactory.stringToKey(key)); 
			}
		
			event.setName(object.getString("name"));
			event.setLocation(object.getString("location"));
			event.setStartDate(new Date(Long.parseLong(object.getString("startDate"))));
			event.setEndDate(new Date(Long.parseLong(object.getString("endDate"))));
			event.setUrl(object.getString("url"));
			event.setMaxAttendees(Integer.parseInt(object.getString("maxAttendees")));
		}catch(JSONException je){
			event = null;
			// TODO handle exception
		}
		return event;
	}
}
