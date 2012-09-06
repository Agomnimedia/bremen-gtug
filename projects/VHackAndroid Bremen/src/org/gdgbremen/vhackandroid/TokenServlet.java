package org.gdgbremen.vhackandroid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.gdgbremen.vhackandroid.commands.TokenCommand;

public final class TokenServlet extends HttpServlet {
	
	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3068247295782686240L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		final TokenCommand cmd = new TokenCommand();
		cmd.init(req, resp, VHackErrors.class);
		cmd.process();
	}
}
