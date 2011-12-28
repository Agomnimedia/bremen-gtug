package org.gtugs.org.eventmanagement.data;


public class Talk {

	private String pkId;
	
	private Attendee speaker;
	
	private String name;

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
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
