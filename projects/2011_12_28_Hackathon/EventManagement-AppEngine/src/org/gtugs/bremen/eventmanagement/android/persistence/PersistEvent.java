package org.gtugs.bremen.eventmanagement.android.persistence;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

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

}
