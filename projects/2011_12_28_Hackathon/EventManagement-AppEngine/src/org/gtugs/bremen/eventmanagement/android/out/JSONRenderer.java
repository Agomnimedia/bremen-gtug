package org.gtugs.bremen.eventmanagement.android.out;

import org.gtugs.bremen.eventmanagement.android.data.Attendee;
import org.gtugs.bremen.eventmanagement.android.data.Event;
import org.gtugs.bremen.eventmanagement.android.data.Talk;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistAtendee;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistEvent;
import org.gtugs.bremen.eventmanagement.android.persistence.PersistentTalk;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class JSONRenderer implements Renderer{

	@Override
	public String renderEvent(Event event, DataAmount amount) {
		String result;
		try{
			final JSONObject json = new JSONObject();
			switch(amount){
			case COMPLETE:
				json.put("key", KeyFactory.keyToString(event.getKey()));
				json.put("name", event.getName());
				json.put("location", event.getLocation());
				json.put("startDate", event.getStartDate().getTime());
				json.put("endDate", event.getEndDate().getTime());
				json.put("url", event.getUrl());
				final JSONArray array = new JSONArray();
				for(Key att : event.getAttendies()){
					array.put(new JSONObject(this.renderAttendee(new PersistAtendee().getAttendeeById(att), DataAmount.LIGHT)));
				}
				json.put("attendees", array);
				json.put("admin", new JSONObject(this.renderAttendee(new PersistAtendee().getAttendeeById(event.getAdmin()), DataAmount.LIGHT)));
				json.put("talks", event.getTalks());
				json.put("maxAttendies", event.getMaxAttendees());
				final JSONArray arrayTalks = new JSONArray();
				for(Key talk : event.getTalks()){
					arrayTalks.put(new JSONObject(this.renderTalk(new PersistentTalk().getTalkById(talk), DataAmount.LIGHT)));
				}
				break;
			case LIGHT:
				json.put("key", KeyFactory.keyToString(event.getKey()));
				json.put("name", event.getName());
				break;
			default:
					// TODO create error 'unhandled dataamount'	
			}		
			result = json.toString();
		}catch(JSONException e){
			// TODO report JSONException
			result = null;
		}
		return result;
	}

	@Override
	public String renderAttendee(Attendee attendee, DataAmount amount) {
		String result;
		try{
			final JSONObject json = new JSONObject();
			switch(amount){
			case COMPLETE:
				json.put("key", KeyFactory.keyToString(attendee.getKey()));
				json.put("nickname", attendee.getNickname());
				json.put("email", attendee.getEmail());
				final JSONArray jsonArray = new JSONArray();
				for(Key ev : attendee.getEvents()){
					jsonArray.put(new JSONObject(this.renderEvent(new PersistEvent().getEventById(ev), DataAmount.LIGHT)));
				}
				json.put("attendees", jsonArray);
				break;
			case LIGHT:
				json.put("key", KeyFactory.keyToString(attendee.getKey()));
				json.put("nickname", attendee.getNickname());
				break;
			default:
					// TODO create error 'unhandled dataamount'
			}
			result = json.toString();
		}catch(JSONException e){
			// TODO report JSONException
			result = null;
		}
		return result;
	}

	@Override
	public String renderTalk(Talk talk, DataAmount amount) {
		String result;
		final JSONObject json = new JSONObject();
		try{
			switch(amount){
			case COMPLETE:
				json.put("key", KeyFactory.keyToString(talk.getKey()));
				json.put("name", talk.getName());
				json.put("speaker", new JSONObject(this.renderAttendee(talk.getSpeaker(), DataAmount.LIGHT)));
				break;
			case LIGHT:
				json.put("key", KeyFactory.keyToString(talk.getKey()));
				json.put("name", talk.getName());
				break;
			default:
				// TODO create error 'unhandled dataamount'
			}
			result = json.toString();
		}catch(JSONException e){
			// TODO report JSONException
			result = null;
		}
		return result;
	}
}
