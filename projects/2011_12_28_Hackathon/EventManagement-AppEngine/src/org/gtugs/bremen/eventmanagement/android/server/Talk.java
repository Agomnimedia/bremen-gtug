package org.gtugs.bremen.eventmanagement.android.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

public class Talk {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key pkId;
	
	@Persistent
	private Attendee speaker;
	
	@Persistent
	private String name;

	public Key getPkId() {
		return pkId;
	}

	public void setPkId(Key pkId) {
		this.pkId = pkId;
	}

	public Attendee getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Attendee speaker) {
		this.speaker = speaker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
