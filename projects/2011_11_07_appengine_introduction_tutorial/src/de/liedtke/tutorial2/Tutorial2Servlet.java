package de.liedtke.tutorial2;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import de.liedtke.tutorial2.data.MyUser;
import de.liedtke.tutorial2.data.PMF;

@SuppressWarnings("serial")
public class Tutorial2Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        final PersistenceManager pManager = PMF.get().getPersistenceManager();

        if (user != null) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello, " + user.getNickname());
            final MyUser myUser = new MyUser();
            myUser.setName(user.getNickname());
            myUser.setLoggedIn(new Date());
            pManager.makePersistent(myUser);
            pManager.close();
        } else {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
	}
}
