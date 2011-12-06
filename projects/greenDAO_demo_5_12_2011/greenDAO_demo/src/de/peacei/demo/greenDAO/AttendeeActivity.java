package de.peacei.demo.greenDAO;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import de.greenrobot.dao.QueryBuilder;
import de.peacei.demo.greenDAO.database.AttendeeEntity;
import de.peacei.demo.greenDAO.database.DaoSession;
import de.peacei.demo.greenDAO.database.EventEntity;
import de.peacei.demo.greenDAO.database.EventEntityDao;

public class AttendeeActivity extends Activity {
	
	private Spinner spinner;
	private boolean update = false;
	
	private AttendeeEntity attendee;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendee_layout);
        
        spinner = ((Spinner) this.findViewById(R.id.eventSpinner));
        
        
        if(Build.VERSION.SDK_INT>10) this.getActionBar().setTitle(R.string.add_attendee);  
        
        
        // getting the QueryBuilder for EventEntity
		DaoSession session = SessionManager.getSession(this.getApplicationContext());
		EventEntityDao eventEntityDao = session.getEventEntityDao();
		QueryBuilder<EventEntity> eventQB = eventEntityDao.queryBuilder();
		
		// getting all events
		List<EventEntity> events = eventQB.list();
        
        
        
        
        
        Intent intent = this.getIntent();
        if(intent.hasExtra("attendeeId")) { 
        	Long key = intent.getExtras().getLong("attendeeId");
        	attendee = session.getAttendeeEntityDao().load(key);
        	EventEntity event = attendee.getEventEntity();
        	events.remove(event);
        	
        	ArrayAdapter<EventEntity> adapter = new ArrayAdapter<EventEntity>(this,
    				android.R.layout.simple_list_item_1, events);
        	adapter.add(event);
        	
        	((EditText) this.findViewById(R.id.nameEdit)).setText(attendee.getName());
        	((EditText) this.findViewById(R.id.secndNameEdit)).setText(attendee.getSecondName());
        	
        	spinner.setAdapter(adapter);
        	spinner.setSelection(adapter.getPosition(event));
        	
        	this.update = true;
        	
        } else {
        	ArrayAdapter<EventEntity> adapter = new ArrayAdapter<EventEntity>(this,
    				android.R.layout.simple_list_item_1, events);
            spinner.setAdapter(adapter);
        }
    }
    
    public void buttonClick(View view) {
    	String name = ((EditText) this.findViewById(R.id.nameEdit)).getText().toString();
    	String secondName = ((EditText) this.findViewById(R.id.secndNameEdit)).getText().toString();
    	
    	// getting the entity-id of the selected event in the spinner
    	EventEntity event = (EventEntity) spinner.getSelectedItem();
    	long eventId = event.getId();
    	
    	DaoSession session = SessionManager.getSession(this.getApplicationContext());
    	
    	if(update) {
    		attendee.setName(name);
    		attendee.setSecondName(secondName);
    		attendee.setEventId(eventId);
    		
    		session.getAttendeeEntityDao().update(attendee);
    	} else {
    		AttendeeEntity attendeeEntity = new AttendeeEntity();
    		attendeeEntity.setName(name);
    		attendeeEntity.setSecondName(secondName);
    		attendeeEntity.setEventId(eventId);
    	
			session.getAttendeeEntityDao().insert(attendeeEntity);
    	}
		
    	finish();
    }
}
