package org.gtugs.bremen.eventReg.servlets;

import javax.servlet.http.HttpServlet;

import org.gtugs.bremen.eventReg.data.Event;
import org.gtugs.bremen.eventReg.data.daos.EventDao;

@SuppressWarnings("serial")
public class PostEventServlet extends HttpServlet {
	
	
	protected void doPost(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
		throws javax.servlet.ServletException, java.io.IOException {
		
		String eventName = req.getParameter("eventname");
		String eventLocation = req.getParameter("eventlocation");
		String eventUrl = req.getParameter("eventurl");
		
		Event event = new Event();
		event.setName(eventName);
		event.setLocation(eventLocation);
		event.setUrl(eventUrl);
		
		
		EventDao eventDao = new EventDao();
		eventDao.insert(event);
	}
}
