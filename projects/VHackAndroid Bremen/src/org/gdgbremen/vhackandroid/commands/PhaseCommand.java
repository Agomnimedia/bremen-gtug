package org.gdgbremen.vhackandroid.commands;

import static org.gdgbremen.vhackandroid.common.Constants.CHANNEL_KEY;
import static org.gdgbremen.vhackandroid.common.Constants.PHASE;

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
		
		try {
			final int phase = Integer.parseInt(this.req.getParameter("phase"));
			PHASE = phase;
			channelService.sendMessage(PHASE, json, req);
			this.handleSuccess("The phase was successfully changed!", null);
		} catch (JSONException e) {
			this.handleError(88);
		} catch(NumberFormatException nfe){
			this.handleError(1);
		} catch(NullPointerException ne){
			this.handleError(2);
		}
		
		super.process();
	}
	
}
