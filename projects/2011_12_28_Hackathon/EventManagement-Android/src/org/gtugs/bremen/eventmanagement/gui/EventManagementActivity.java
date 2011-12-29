/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.gtugs.bremen.eventmanagement.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

/**
 * Main activity - requests "Hello, World" messages from the server and provides
 * a menu item to invoke the accounts activity.
 */
public class EventManagementActivity extends FragmentActivity {
    
	public static final String SENDER_FRAGMENT = "sender";
	public static final String FRAGMENT_TO_START = "toStart";
	
	public static final byte DASHBOARD_FRAGMENT = 0;
	public static final byte MY_EVENTS_FRAGMENT = 1;
	public static final byte ALL_EVENTS_FRAGMENT = 2;
	public static final byte EVENT_DETAILS_FRAGMENT = 3;
	public static final byte CREATE_EDIT_EVENT_FRAGMENT = 4;
	public static final byte TASK_FRAGMENT = 5;

    private boolean isMultiPane=true;

    

    /**
     * Begins the activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	this.setContentView(R.layout.event_manage_activity);
    	
    	if(this.findViewById(R.id.leftContainer) == null) this.isMultiPane=false;
    	
    	FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
    	
    	if(isMultiPane) {
    		transaction.add(R.id.leftContainer, new DashboardFragment(), "leftPane");
    		transaction.add(R.id.rightContainer, new MyEventsFragment(), "rightPane");
    	} else {
    		transaction.add(R.id.rootView, new DashboardFragment(), "dashboardFragment");
    	}
    	
    	transaction.commit();
    }
    
  

    @Override
    public void onResume() {
        super.onResume();

        
    }
    
    public void startFragment(Bundle bundle) {
    	FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
    	
		byte sender = bundle.getByte(SENDER_FRAGMENT);
		byte toStart = bundle.getByte(FRAGMENT_TO_START);
		
		if(isMultiPane) {    			 
    		
    		switch(sender) {
    			case DASHBOARD_FRAGMENT:
    				switch(toStart) {
    					case ALL_EVENTS_FRAGMENT:
    						transaction.replace(R.id.rightContainer, new AllEventsFragment(), "rightPane");
    					break;
    					case MY_EVENTS_FRAGMENT:
    						transaction.replace(R.id.rightContainer, new MyEventsFragment(), "rightPane");
    					break;
    					default:
    				}
    			break;
    			case ALL_EVENTS_FRAGMENT:
    				Fragment allEvFrag = manager.findFragmentByTag("rightPane");
    				transaction.replace(R.id.leftContainer, allEvFrag, "leftPane");
    				transaction.replace(R.id.rightContainer, new EventDetailFragment(), "rightPane");
    			break;
    			default:
    		}
    		
    	} else {
    		switch(sender) {
				case DASHBOARD_FRAGMENT:
					switch(toStart) {
						case ALL_EVENTS_FRAGMENT:
							transaction.replace(R.id.rootView, new AllEventsFragment(), "allEvents");
							break;
						default:
					}
					break;
				default:
    		}
    	}
    	transaction.addToBackStack(null);
    	transaction.commit();
    	
    }

    /**
     * Shuts down the activity.
     */
    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        // Invoke the Register activity
//        menu.getItem(0).setIntent(new Intent(this, AccountsActivity.class));
//        return true;
//    }

    // Manage UI Screens

//    private void setHelloWorldScreenContent() {
//        setContentView(R.layout.hello_world);
//
//        final TextView helloWorld = (TextView) findViewById(R.id.hello_world);
//        final Button sayHelloButton = (Button) findViewById(R.id.say_hello);
//        sayHelloButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                sayHelloButton.setEnabled(false);
//                helloWorld.setText(R.string.contacting_server);
//
//                // Use an AsyncTask to avoid blocking the UI thread
//                new AsyncTask<Void, Void, String>() {
//                    private String message;
//
//                    @Override
//                    protected String doInBackground(Void... arg0) {
//                        MyRequestFactory requestFactory = Util.getRequestFactory(mContext,
//                                MyRequestFactory.class);
//                        final HelloWorldRequest request = requestFactory.helloWorldRequest();
//                        Log.i(TAG, "Sending request to server");
//                        request.getMessage().fire(new Receiver<String>() {
//                            @Override
//                            public void onFailure(ServerFailure error) {
//                                message = "Failure: " + error.getMessage();
//                            }
//
//                            @Override
//                            public void onSuccess(String result) {
//                                message = result;
//                            }
//                        });
//                        return message;
//                    }
//
//                    @Override
//                    protected void onPostExecute(String result) {
//                        helloWorld.setText(result);
//                        sayHelloButton.setEnabled(true);
//                    }
//                }.execute();
//            }
//        });
//    }

//    /**
//     * Sets the screen content based on the screen id.
//     */
//    private void setScreenContent(int screenId) {
//        setContentView(screenId);
//        switch (screenId) {
//            case R.layout.hello_world:
//                setHelloWorldScreenContent();
//                break;
//        }
//    }
}
