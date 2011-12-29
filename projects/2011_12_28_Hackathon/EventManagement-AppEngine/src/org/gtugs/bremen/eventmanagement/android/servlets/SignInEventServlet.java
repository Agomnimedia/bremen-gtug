package org.gtugs.bremen.eventmanagement.android.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class SignInEventServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws javax.servlet.ServletException, 
					java.io.IOException {
		
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        
		final String key = (String) req.getParameter("key");

		Key eventKey;
		boolean successful = false;
		if(key != null){
			eventKey = KeyFactory.stringToKey(key);
			successful = new PersistEvent().signInEvent(eventKey, user);
		}
		
		final JSONObject result = new JSONObject();
		try {
			if(successful){
				result.put("signIn", true);
			}else{
				result.put("signIn", false);
			}
		} catch (JSONException e) {
			// TODO return error
		}
		resp.setContentType("text/plain");
		resp.getWriter().append(result.toString());
	}

}
