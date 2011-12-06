package de.peacei.demo.greenDAO;

import android.content.Context;
import de.peacei.demo.greenDAO.database.DaoMaster;
import de.peacei.demo.greenDAO.database.DaoMaster.OpenHelper;
import de.peacei.demo.greenDAO.database.DaoSession;

public class SessionManager {
	
	private static DaoSession sessionInstance = null;
	
	public static DaoSession getSession(Context context) {
		
		if(sessionInstance == null) {
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, "demodatabase", null);
			DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
			
			sessionInstance = daoMaster.newSession();
		}
		
		return sessionInstance;
	}

}
