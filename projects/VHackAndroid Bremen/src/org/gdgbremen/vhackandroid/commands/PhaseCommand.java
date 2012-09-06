package org.gdgbremen.vhackandroid.commands;

import static org.gdgbremen.vhackandroid.common.Constants.CHANNEL_KEY;

import java.io.IOException;

import javax.servlet.ServletException;

import org.gdgbremen.vhackandroid.DomainFactory;
import org.gdgbremen.vhackandroid.domain.api.VHackChannelService;
import org.json.JSONException;

import de.liedtke.presentation.WebCommand;

public final class PhaseCommand extends WebCommand{

	@Override
	public void process() throws ServletException, IOException {
		final VHackChannelService channelService = DomainFactory.createChannelService();
		this.registerService(channelService);
		
		if(CHANNEL_KEY == null){
			channelService.channelToken();
		}
		
		// TODO collect data for the phase
		
		try {
			channelService.sendMessage(1, json);
			this.handleSuccess("The phase was successfully changed!", null);
		} catch (JSONException e) {
			this.handleError(88);
		}
		
		super.process();
	}
	
}
