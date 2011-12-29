package org.gtugs.bremen.eventmanagement.android.data;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Event {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key pkId;
	
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
	@ManyToMany
	private List<Attendee> attendies;
	
	@Persistent
	@ManyToOne
	private Attendee admin;
	
	@Persistent
	private List<Talk> talks;
	
	@Persistent
	//Default 0
	private int maxAttendees = 0;

	
	public Key getPkId() {
		return pkId;
	}

	public void setPkId(Key pkId) {
		this.pkId = pkId;
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

	public List<Attendee> getAttendies() {
		return attendies;
	}

	public void setAttendies(List<Attendee> attendies) {
		this.attendies = attendies;
	}

	public Attendee getAdmin() {
		return admin;
	}

	public void setAdmin(Attendee admin) {
		this.admin = admin;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	public int getMaxAttendees() {
		return maxAttendees;
	}

	public void setMaxAttendees(int maxAttendees) {
		this.maxAttendees = maxAttendees;
	}
	
	
	
}
