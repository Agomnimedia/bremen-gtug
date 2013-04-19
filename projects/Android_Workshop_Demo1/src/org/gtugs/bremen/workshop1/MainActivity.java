package org.gtugs.bremen.workshop1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

}
