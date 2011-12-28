package org.gtugs.org.eventmanagement.data;

import java.util.Date;
import java.util.List;

public class Event {
	
	private String pkId;
	
	private String name;
	
	private String location;
	
	private Date startDate;
	
	private Date endDate;
	
	private String url;
	
	private List<Attendee> attendies;
	
	private Attendee admin;
	
	private List<Talk> talks;
	
	//Default 0
	private int maxAttendees = 0;

	
	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
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
