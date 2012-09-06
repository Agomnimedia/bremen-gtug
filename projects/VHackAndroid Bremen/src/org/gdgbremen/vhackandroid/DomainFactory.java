package org.gdgbremen.vhackandroid;

import org.gdgbremen.vhackandroid.domain.ChannelServiceImpl;
import org.gdgbremen.vhackandroid.domain.api.VHackChannelService;

public final class DomainFactory {

	
	public static final VHackChannelService createChannelService(){
		return new ChannelServiceImpl();
	}
}
