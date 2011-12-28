package org.gtugs.bremen.eventmanagement.android.persistence;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.server.PMF;
import org.gtugs.bremen.eventmanagement.android.server.Update;

public class PersistUpdate {

	public void insert(Update update) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(update);
			pm.flush();
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Update findLastUpdate(final int kind){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		final Query query = pm.newQuery(Update.class, "this.kind == kind && ");
		query.declareParameters("int age");
		query.setOrdering("date descending");
		query.setRange(0, 0);
		final List<Update> updates = (List<Update>) query.execute(Integer.valueOf(kind));
		return updates.get(0);
	}
}
