package org.gdgbremen.vhackandroid.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import org.json.JSONException;
import org.json.JSONObject;

import de.liedtke.presentation.WebCommand;

import static org.gdgbremen.vhackandroid.common.Constants.PHASE;

public class PhaseInformationCommand extends WebCommand{

	
	@Override
	public void process() throws ServletException, IOException {
		try {
			final JSONObject jsonMessage = new JSONObject();
			jsonMessage.put("phase", PHASE);
			this.handleSuccess("phase was found", jsonMessage.toString());
		} catch (JSONException e) {
			this.handleError(88);
		}
		
		super.process();
	}
}
