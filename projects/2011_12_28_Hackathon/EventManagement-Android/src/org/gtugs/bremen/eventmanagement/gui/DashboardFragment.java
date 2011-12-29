/**
 * 
 */
package org.gtugs.bremen.eventmanagement.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * @author peacei
 *
 */
public class DashboardFragment extends Fragment implements OnClickListener {
	
	public DashboardFragment() {	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View rootView = (View) inflater.inflate(R.layout.dashboard_fragment, container, false);
		
		((Button) rootView.findViewById(R.id.btn_event)).setOnClickListener(this);
		//((Button) rootView.findViewById(R.id.btn_event)).setOnClickListener(this);
		
        // Inflate the layout for this fragment
        return rootView; 
    }
	


	@Override
	public void onClick(View arg0) {
		Bundle bundle = new Bundle();
		bundle.putByte(EventManagementActivity.FRAGMENT_TO_START, EventManagementActivity.ALL_EVENTS_FRAGMENT);
		bundle.putByte(EventManagementActivity.SENDER_FRAGMENT, EventManagementActivity.DASHBOARD_FRAGMENT);
		((EventManagementActivity) getActivity()).startFragment(bundle);
		
	}
	
	
	
//	/**
//     * Tag for logging.
//     */
//    private static final String TAG = "EventManagementActivity";
//
//    /**
//     * The current context.
//     */
//    private Context mContext = this;
//
//    /**
//     * A {@link BroadcastReceiver} to receive the response from a register or
//     * unregister request, and to update the UI.
//     */
//    private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String accountName = intent.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
//            int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA,
//                    DeviceRegistrar.ERROR_STATUS);
//            String message = null;
//            String connectionStatus = Util.DISCONNECTED;
//            if (status == DeviceRegistrar.REGISTERED_STATUS) {
//                message = getResources().getString(R.string.registration_succeeded);
//                connectionStatus = Util.CONNECTED;
//            } else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
//                message = getResources().getString(R.string.unregistration_succeeded);
//            } else {
//                message = getResources().getString(R.string.registration_error);
//            }
//
//            // Set connection status
//            SharedPreferences prefs = Util.getSharedPreferences(mContext);
//            prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();
//
//            // Display a notification
//            Util.generateNotification(mContext, String.format(message, accountName));
//        }
//    };
//
//    /**
//     * Begins the activity.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        Log.i(TAG, "onCreate");
//        super.onCreate(savedInstanceState);
//        
//        // Register a receiver to provide register/unregister notifications
//        registerReceiver(mUpdateUIReceiver, new IntentFilter(Util.UPDATE_UI_INTENT));
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        SharedPreferences prefs = Util.getSharedPreferences(mContext);
//        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
//        if (Util.DISCONNECTED.equals(connectionStatus)) {
//            startActivity(new Intent(this, AccountsActivity.class));
//        }
//        setContentView(R.layout.welcome);
//    }
//
//    /**
//     * Shuts down the activity.
//     */
//    @Override
//    public void onDestroy() {
//        unregisterReceiver(mUpdateUIReceiver);
//        super.onDestroy();
//    }

}
