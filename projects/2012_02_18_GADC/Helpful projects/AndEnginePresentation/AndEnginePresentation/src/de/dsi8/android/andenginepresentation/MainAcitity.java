package de.dsi8.android.andenginepresentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainAcitity extends Activity {
    
    /**
     * Initialisierung
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	
    /**
     * Shows the first iteration
     * @param view Sender
     */
    public void showIteration(View view) {
    	Intent intent = new Intent();
    	switch (view.getId()) {
		case R.id.iteration1Btn:
			intent.setClass(getApplicationContext(), Iteration1Activity.class);
			break;
		case R.id.iteration2Btn:
			intent.setClass(getApplicationContext(), Iteration2Activity.class);
			break;
		case R.id.iteration3Btn:
			intent.setClass(getApplicationContext(), Iteration3Activity.class);
			break;
		case R.id.iteration4Btn:
			intent.setClass(getApplicationContext(), Iteration4Activity.class);
			break;
		default:
			return;
		}

        startActivity(intent);
    }
}
