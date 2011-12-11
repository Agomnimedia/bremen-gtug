package de.peacei.demo.greenDAO;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.greenrobot.dao.QueryBuilder;
import de.peacei.demo.greenDAO.database.AttendeeEntity;
import de.peacei.demo.greenDAO.database.AttendeeEntityDao;
import de.peacei.demo.greenDAO.database.DaoSession;

public class GreenDAO_demoActivity extends Activity {
    
	ListView listView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        listView = (ListView) this.findViewById(R.id.listView);
        
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	refresh();
    }
    
    public void attendeeButtonClick(View view) {
    	Intent intent = new Intent(this, AttendeeActivity.class);
    	this.startActivity(intent);
    }
    
    public void eventButtonClick(View view) {
    	Intent intent = new Intent(this, EventActivity.class);
    	this.startActivity(intent);
    }
    
    private void refresh() {
    	DaoSession session = SessionManager.getSession(getApplicationContext());
		
        // getting the QueryBuilder for EventEntity
		AttendeeEntityDao attendeeEntityDao = session.getAttendeeEntityDao();
		QueryBuilder<AttendeeEntity> attendeeQB = attendeeEntityDao.queryBuilder();
		// getting all attendees
		List<AttendeeEntity> attendees = attendeeQB.list();
		
		
		if(attendees == null) {
			Toast toast = Toast.makeText(this.getApplicationContext(), this.getString(R.string.no_data), Toast.LENGTH_SHORT);
	        toast.show();
		} else {
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
					AttendeeEntity attendee = (AttendeeEntity) listView.getItemAtPosition(position); 
					long key = attendee.getId();
					Intent intent = new Intent(GreenDAO_demoActivity.this, AttendeeActivity.class);
					intent.putExtra("attendeeId", key);
					startActivity(intent);
				}
			});
			
			final ArrayAdapter<AttendeeEntity> adapter = new ArrayAdapter<AttendeeEntity>(this,
					android.R.layout.simple_list_item_1, attendees);
			listView.setAdapter(adapter);
		}
    }
}