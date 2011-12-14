package org.gtugs.bremen.eventReg.data.daos;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventReg.data.Event;
import org.gtugs.bremen.eventReg.util.PMF;


public class EventDao {
	
	
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
