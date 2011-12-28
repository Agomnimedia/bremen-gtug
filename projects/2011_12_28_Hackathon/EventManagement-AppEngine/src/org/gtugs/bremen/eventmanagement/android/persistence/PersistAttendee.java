package org.gtugs.bremen.eventmanagement.android.persistence;

import javax.jdo.PersistenceManager;


import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.server.Attendee;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

import com.google.appengine.api.users.User;

public class PersistAttendee {

	
	public boolean isAdmin(User user){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		//Load atteende with email adress from user object.

		Query query = pm.newQuery(Attendee.class,"this.email == email");
		query.declareParameters("String email");
		Attendee attendee = (Attendee) query.execute(user.getEmail());
		return attendee.isAdmin();
	}
}
