package org.gdgbremen.vhackandroid.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import org.gdgbremen.vhackandroid.DomainFactory;
import org.gdgbremen.vhackandroid.domain.api.VHackChannelService;
import org.json.JSONException;
import org.json.JSONObject;

import de.liedtke.presentation.WebCommand;

public final class TokenCommand extends WebCommand {

	
	
	@Override
	public void process() throws ServletException, IOException {
		final VHackChannelService channelService = DomainFactory.createChannelService();
		this.registerService(channelService);
		try {
			final JSONObject json = new JSONObject();
			
			json.put("token", channelService.channelToken());
		    
			this.handleSuccess("Token was successfully fetched", json.toString());
		} catch (JSONException e) {
			this.handleError(88);
		}

		super.process();
	}
}
