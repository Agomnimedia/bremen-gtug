package org.gtugs.bremen.eventmanagement.android.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.persistence.PersistAtendee;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class IsAdminServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws javax.servlet.ServletException, 
			java.io.IOException {
		
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        
        boolean isAdmin = new PersistAtendee().isAdmin(user);
        resp.setContentType("text/plain");
        resp.getWriter().append(isAdmin == true ? "true" : "false");
		
	}
}
