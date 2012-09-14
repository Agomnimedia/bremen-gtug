package org.gdgbremen.vhackandroid.domain;

import static org.gdgbremen.vhackandroid.common.Constants.CHANNEL_KEY;

import javax.servlet.http.HttpServletRequest;


import org.gdgbremen.vhackandroid.domain.api.VHackChannelService;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import de.liedtke.business.BasicServiceImpl;
import de.liedtke.util.Generator;

public class ChannelServiceImpl extends BasicServiceImpl implements VHackChannelService{

	private static String TOKEN = null;
	
	@Override
	public String channelToken() {
		if(CHANNEL_KEY == null || TOKEN == null){
			CHANNEL_KEY = Generator.randomString(20);
			final ChannelService channelService = ChannelServiceFactory.getChannelService();
		    TOKEN = channelService.createChannel(CHANNEL_KEY);
		}
		return TOKEN;
	}

	@Override
	public void sendMessage(int phaseNumber, JSONObject json, HttpServletRequest req) throws JSONException {
		final ChannelService channelService = ChannelServiceFactory.getChannelService();
		final JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("phase", phaseNumber);
		if(json != null){
			jsonMessage.put("data", json);
		}
		channelService.sendMessage(new ChannelMessage(CHANNEL_KEY, jsonMessage.toString()));
	}

}
