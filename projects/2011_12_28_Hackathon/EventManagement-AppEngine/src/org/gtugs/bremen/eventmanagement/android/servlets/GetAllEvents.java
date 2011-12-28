package org.gtugs.bremen.eventmanagement.android.servlets;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.naming.event.EventDirContext;
import javax.servlet.http.HttpServlet;

import org.gtugs.bremen.eventmanagement.android.server.Attendee;
import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.gtugs.bremen.eventmanagement.android.server.Talk;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class GetAllEvents extends HttpServlet{

	protected void doPost(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
		throws javax.servlet.ServletException, java.io.IOException {
		
		
	
		String name = req.getParameter("name");
		String location = req.getParameter("LOCATION");
//		Date startDate = req.getParameter("STARTDATE");
		Date endDate;
		String url;
		List<Attendee> attendies;
		Attendee admin;
		List<Talk> talks;
		int maxAttendees = 0;
		
		
		Event event = new Event();
		
	}

}
