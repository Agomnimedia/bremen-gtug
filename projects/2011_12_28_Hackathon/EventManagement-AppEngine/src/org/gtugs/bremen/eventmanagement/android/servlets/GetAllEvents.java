package org.gtugs.bremen.eventmanagement.android.servlets;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
import org.gtugs.bremen.eventmanagement.android.server.Attendee;
import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.gtugs.bremen.eventmanagement.android.server.Talk;

@SuppressWarnings("serial")
public class GetAllEvents extends HttpServlet{

	protected void doPost(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
		throws javax.servlet.ServletException, java.io.IOException {
		
		
		PersistEvent persistEvent = PersistEvent.
		Event event = new Event();
		
		
	
		String name = event.getName();
		String location = event.getLocation();
		Date startDate = event.getStartDate();
		Date endDate = event.getEndDate();
		String url = event.getUrl();
		List<Attendee> attendies = event.getAttendies();
		Attendee admin;
		List<Talk> talks = event.getTalks();
		int maxAttendees = event.getMaxAttendees();
		
		
		
		
	}

}
