package org.gtugs.bremen.eventmanagement.android.persistence;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

import com.google.appengine.api.datastore.Key;

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

}
