package org.gdgbremen.vhackandroid.domain.api;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import de.liedtke.business.api.BasicService;

public interface VHackChannelService extends BasicService{

	String channelToken();
	
	void sendMessage(int phaseNumber, JSONObject json, HttpServletRequest req) throws JSONException;
}
