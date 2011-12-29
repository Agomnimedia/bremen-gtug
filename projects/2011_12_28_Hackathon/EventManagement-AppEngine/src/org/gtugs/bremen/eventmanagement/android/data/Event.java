package org.gtugs.bremen.eventmanagement.android.data;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Event {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private String location;
	
	@Persistent
	private Date startDate;
	
	@Persistent
	private Date endDate;
	
	@Persistent
	private String url;
	
	@Persistent
	private List<Key> attendies;
	
	@Persistent
	private Key admin;
	
	@Persistent
	private List<Key> talks;
	
	@Persistent
	//Default 0
	private int maxAttendees = 0;

	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Key> getAttendies() {
		return attendies;
	}

	public void setAttendies(List<Key> attendies) {
		this.attendies = attendies;
	}

	public Key getAdmin() {
		return admin;
	}

	public void setAdmin(Key admin) {
		this.admin = admin;
	}

	public List<Key> getTalks() {
		return talks;
	}

	public void setTalks(List<Key> talks) {
		this.talks = talks;
	}

	public int getMaxAttendees() {
		return maxAttendees;
	}

	public void setMaxAttendees(int maxAttendees) {
		this.maxAttendees = maxAttendees;
	}
}
