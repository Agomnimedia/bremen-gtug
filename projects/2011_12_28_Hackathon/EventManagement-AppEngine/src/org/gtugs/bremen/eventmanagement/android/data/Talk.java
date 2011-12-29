package org.gtugs.bremen.eventmanagement.android.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Talk {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Attendee speaker;
	
	@Persistent
	private String name;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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
