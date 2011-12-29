package org.gtugs.bremen.eventmanagement.android.persistence;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.data.Attendee;
import org.gtugs.bremen.eventmanagement.android.data.Event;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

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
	
	
	public Attendee getUser(User user) throws IllegalArgumentException{
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Attendee attendee = null;
		try{
			final Query query = pManager.newQuery(Attendee.class, "this.email==email");
			query.declareParameters("String email");
			attendee = (Attendee) query.execute(user.getEmail());
		} finally{
			pManager.close();
		}
		
		return attendee;
	}
	
	public boolean isAdmin(User user){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		//Load atteende with email adress from user object.

		Query query = pm.newQuery(Attendee.class,"this.email == email");
		query.declareParameters("String email");
		Attendee attendee = (Attendee) query.execute(user.getEmail());
		return attendee.isAdmin();
	}
}
