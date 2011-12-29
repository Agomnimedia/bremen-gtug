package org.gtugs.bremen.eventmanagement.android.data;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Attendee {
	
	//Muss als String verschickt werden
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String nickname;
	
	@Persistent
	private String email;
	
	@Persistent
	@ManyToMany
	private List<Event> events;
	
	@Persistent
	@ManyToOne
	//Default is false
	private boolean admin = false;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
}
