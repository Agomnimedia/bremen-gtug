package de.peacei.demo.greenDAO;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.peacei.demo.greenDAO.database.DaoSession;
import de.peacei.demo.greenDAO.database.EventEntity;

public class EventActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_layout);
        
        if(Build.VERSION.SDK_INT>10) this.getActionBar().setTitle(R.string.add_event);
    }
    
    public void buttonClick(View view) {
    	String name = ((EditText) this.findViewById(R.id.nameEdit)).getText().toString();
    	String location = ((EditText) this.findViewById(R.id.locationEdit)).getText().toString();
    	
		
		DaoSession session = SessionManager.getSession(this.getApplicationContext());
		
		EventEntity eventEntity = new EventEntity();
		eventEntity.setName(name);
		eventEntity.setLocation(location);
		
		session.getEventEntityDao().insert(eventEntity);
		
    	
    	finish();
    }
}