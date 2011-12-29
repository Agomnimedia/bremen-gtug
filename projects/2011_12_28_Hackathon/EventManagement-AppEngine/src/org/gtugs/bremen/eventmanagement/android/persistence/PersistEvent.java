package org.gtugs.bremen.eventmanagement.android.persistence;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.data.Attendee;
import org.gtugs.bremen.eventmanagement.android.data.Event;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

public class PersistEvent {
	
	@SuppressWarnings("unchecked")
	public List<Event> getAllEvents() {
		final PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		Query query = pManager.newQuery(Event.class);
		
		try {
			return (List<Event>) query.execute();
		} catch(ClassCastException ex) {
			return null;
		} finally {
			//pManager.close();
		}
		
	}
	
	
	public void insert(Event event) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(event);
			pm.flush();
		} finally {
			pm.close();
		}
	}
	
	public void delete(Event event){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.deletePersistent(event);
			pm.flush();
		} finally {
			pm.close();
		}
	}
	
	public Event getEventDetails(Key id) throws IllegalArgumentException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Event event;
		try{
			event = (Event) pm.getObjectById(id);
			pm.flush();
		}catch (IllegalArgumentException e) {
			throw e;
		}
		finally {
			pm.close();
		}
		return event;
	}
	
	public boolean signInEvent(Key id, User user){
		//TODO: Email adressen aus der Datenbank laden
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Event event = (Event) pm.getObjectById(id);
		boolean allowedToSign = false;
		Query query = pm.newQuery(Attendee.class);
		Attendee attendee = new Attendee();
		try {
			//Check if user exist in attendee datastorage.
			List<Attendee> attendees = (List<Attendee>) query.execute();
			for(Attendee pers : attendees){
				if(pers.getEmail().equals(user.getEmail())){
					allowedToSign = true;
					attendee = pers;
				}
			}
			
			//User darf sich eintragen
			if(allowedToSign && event != null){
				//Maximale Anzahl ist erreicht.
				if((event.getMaxAttendees() != 0) && event.getAttendies().size() == event.getMaxAttendees()){
					return false;
				} else {
					if(attendee != null)
					event.getAttendies().add(attendee);
				}
			} else {
				return false;
			}
			
		} catch(ClassCastException ex) {
			return false;
		} finally {
			pm.close();
		}
		return true;
	}

}
