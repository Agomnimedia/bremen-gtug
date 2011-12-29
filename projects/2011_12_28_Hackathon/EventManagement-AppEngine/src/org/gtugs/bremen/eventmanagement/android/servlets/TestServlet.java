package org.gtugs.bremen.eventmanagement.android.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class TestServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(TestServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
//		
		if(user != null){
			log.info("user " + user.getEmail());
			resp.getWriter().append("user " + user.getNickname());
		}else{
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}
	}
}
