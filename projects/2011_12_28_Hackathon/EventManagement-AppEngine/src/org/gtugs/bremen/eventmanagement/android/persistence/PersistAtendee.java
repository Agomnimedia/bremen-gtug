package org.gtugs.bremen.eventmanagement.android.persistence;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.server.Attendee;
import org.gtugs.bremen.eventmanagement.android.server.Event;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

import com.google.appengine.api.datastore.Key;

public class PersistAtendee {
	
	public List<Attendee> getAllAttendees(){
		final PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		Query query = pManager.newQuery(Attendee.class);
		
		
		try {
			return (List<Attendee>) query.execute();
		} catch (Exception e) {
			return null;		
		} finally {
			//pManager.close();
		}
	}
	
	public void insert(Attendee attendee){
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		try {
			pManager.makePersistent(attendee);
			pManager.flush();
		} finally {
			pManager.close();
		}
	}
	
	public void delete(Attendee attendee){
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		try {
			pManager.deletePersistent(attendee);
			pManager.flush();
		} finally {
			pManager.close();
		}
	}
	
	
	public Attendee getUser(Key id) throws IllegalArgumentException{
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Attendee attendee;
		try{
			attendee = (Attendee) pManager.getObjectById(id);
			pManager.flush();
		} catch (IllegalArgumentException e) {
			throw e;
			
		} finally{
			pManager.close();
		}
		
		return attendee;
	}
}
