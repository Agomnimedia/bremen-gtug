package gdg.bremen.workshop.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends Activity {

	public static final String EXTRA_SUM1 = "sum1";
	public static final String EXTRA_SUM2 = "sum2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		// Show the Up button in the action bar.
		setupActionBar();

		Intent intent = getIntent();
		int sum1 = intent.getIntExtra(EXTRA_SUM1, 0);
		int sum2 = intent.getIntExtra(EXTRA_SUM2, 0);
		TextView textView = (TextView) findViewById(R.id.textView2);
		String result = Integer.valueOf(sum1 + sum2).toString();
		textView.setText(result);
		Log.i("SecondActivity", "-> onCreate");
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Called when the user clicks the button */
	public void backToFirstActivity(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Log.i("SecondActivity", "-> onStart");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		
		Log.i("SecondActivity", "-> onRestart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Log.i("SecondActivity", "-> onResume");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		Log.i("SecondActivity", "-> onStop");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		Log.i("SecondActivity", "-> onPause");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Log.i("SecondActivity", "-> onDestroy");
	}

}
