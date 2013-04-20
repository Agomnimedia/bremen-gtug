package gdg.bremen.workshop.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i("MainActivity", "-> onCreate");
		
		Toast toast = Toast.makeText(getApplicationContext(), "The MainActivity will be created now", Toast.LENGTH_SHORT);
		toast.show();
		
		final RelativeLayout rootView = (RelativeLayout) findViewById(R.id.rootView);
		rootView.setTag("DEFAULT");
		
		rootView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				if(((String)rootView.getTag()).equals("DEFAULT")){
					rootView.setTag("GRAY");
					rootView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
				}else{
					rootView.setTag("DEFAULT");
					rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
				}
				return true;
			}
		});
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		final RelativeLayout rootView = (RelativeLayout) findViewById(R.id.rootView);
		
		final boolean backgroundDefault = (Boolean)savedInstanceState.get("backgroundColorDefault");
		if(!backgroundDefault){
			rootView.setTag("GRAY");
			rootView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {

		
		final RelativeLayout rootView = (RelativeLayout) findViewById(R.id.rootView);
		outState.putBoolean("backgroundColorDefault", ((String)rootView.getTag()).equals("DEFAULT"));
		
		super.onSaveInstanceState(outState);
	}

	/** Called when the user clicks the button */
	public void openSecondActivity(View view) {
		Intent intent = new Intent(this, SecondActivity.class);
		EditText editText = (EditText) findViewById(R.id.editText1);
		String input = editText.getText().toString();
		int sum = 0;
		if (input.length() > 0) {
			sum = Integer.parseInt(input);
		}
		intent.putExtra(SecondActivity.EXTRA_SUM1, sum);
		editText = (EditText) findViewById(R.id.editText2);
		input = editText.getText().toString();
		sum = 0;
		if (input.length() > 0) {
			sum = Integer.parseInt(input);
		}
		intent.putExtra(SecondActivity.EXTRA_SUM2, sum);
		this.startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Log.i("MainActivity", "-> onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Initialize components that you release during onPause()
		Log.i("MainActivity", "-> onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		// Stop animations or other ongoing actions that could consume CPU.
		
		// Commit unsaved changes, but only if users expect such changes to 
		// be permanently saved when they leave (such as a draft email).
		
		// Release system resources, such as broadcast receivers, handles to 
		// sensors (like GPS), or any resources that may affect battery life 
		// while your activity is paused and the user does not need them.
		
		// You should avoid performing CPU-intensive work
		
		Log.i("MainActivity", "-> onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// It's important you use onStop() to release resources that might leak memory
		
		Toast toast = Toast.makeText(getApplicationContext(), "The MainActivity will be stopped now", Toast.LENGTH_SHORT);
		toast.show();
		
		Log.i("MainActivity", "-> onStop");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		
		// Activity being restarted from stopped state  
		
		Log.i("MainActivity", "-> onRestart");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Log.i("MainActivity", "-> onDestroy");
		
		// Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}

}
