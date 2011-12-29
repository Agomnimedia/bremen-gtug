package org.gtugs.bremen.eventmanagement.android.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.data.Attendee;
import org.gtugs.bremen.eventmanagement.android.data.Event;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistAtendee;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



public class HelperServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -6884295907666630860L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String kind = req.getParameter("kind");
		if(kind.equals("attendee")){
			final Attendee attendee = new Attendee();
			attendee.setNickname(req.getParameter("nickname"));
			attendee.setEmail(req.getParameter("email"));
			if(req.getParameter("admin").equals("true")){
				attendee.setAdmin(true);	
			}
			if(attendee.getNickname() == null || attendee.getEmail() == null){
				resp.setStatus(400);
			}else{
				new PersistAtendee().insert(attendee);
			}
		}else if(kind.equals("event")){
			UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
	        
	        if(user == null){
	        	resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	        }else{
	        	final Event event = new Event();
				event.setAdmin(new PersistAtendee().getUser(user).getKey());
				event.setName(req.getParameter("name"));
				event.setUrl(req.getParameter("url"));
				event.setLocation(req.getParameter("location"));
				try{
					event.setStartDate(new Date(Long.parseLong(req.getParameter("startDate"))));
					event.setEndDate(new Date(Long.parseLong(req.getParameter("endDate"))));
					event.setMaxAttendees(Integer.parseInt(req.getParameter("maxAttendees")));
					if(event.getName() == null || event.getUrl() == null || event.getLocation() == null || 
							event.getStartDate() == null || event.getEndDate() == null){
						resp.setStatus(400);
					}else{
						new PersistEvent().insert(event);
					}
				}catch(NumberFormatException nfe){
					resp.setStatus(400);
				}
	        }
			
		}else{
			resp.setStatus(400);
		}
	}
}
