package org.gdgbremen.vhackandroid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gdgbremen.vhackandroid.commands.PhaseCommand;
import org.gdgbremen.vhackandroid.commands.PhaseInformationCommand;

public class PhaseServlet extends HttpServlet {


	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 8777761926797857151L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final PhaseInformationCommand cmd = new PhaseInformationCommand();
		cmd.init(req, resp, VHackErrors.class);
		cmd.process();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		final PhaseCommand cmd = new PhaseCommand();
		cmd.init(req, resp, VHackErrors.class);
		cmd.process();
	}
}