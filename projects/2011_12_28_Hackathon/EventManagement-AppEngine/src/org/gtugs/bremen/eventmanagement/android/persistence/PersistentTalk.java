package org.gtugs.bremen.eventmanagement.android.persistence;

import javax.jdo.PersistenceManager;

import org.gtugs.bremen.eventmanagement.android.data.Talk;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

import com.google.appengine.api.datastore.Key;

public class PersistentTalk {

	public Talk getTalkById(Key key){
		final PersistenceManager pManager = PMF.get().getPersistenceManager();
		
		Talk result = (Talk) pManager.getObjectById(key);
		
		return result;
		
	}
}
