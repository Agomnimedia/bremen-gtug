package org.gtugs.bremen.eventmanagement.android.persistence;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.bremen.eventmanagement.android.data.Update;
import org.gtugs.bremen.eventmanagement.android.server.PMF;

import com.google.gwt.core.ext.typeinfo.NotFoundException;

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
		final Update result;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		final Query query = pm.newQuery(Update.class, "this.kind == kind");
		query.declareParameters("int age");
		query.setOrdering("date descending");
		query.setRange(0, 0);
		final List<Update> updates = (List<Update>) query.execute(Integer.valueOf(kind));
		if(updates.isEmpty()){
			result = null;
		}else{
			result = updates.get(0);
		}
		return result;
	}
}
