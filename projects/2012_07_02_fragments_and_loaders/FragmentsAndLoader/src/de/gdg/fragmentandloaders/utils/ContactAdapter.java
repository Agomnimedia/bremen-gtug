package de.gdg.fragmentandloaders.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.Contacts.People;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

public class ContactAdapter extends SimpleCursorAdapter {

	public ContactAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}

	
	@Override
	public void bindView(View v, Context ctx, final Cursor c){
		final TextView tv = (TextView) v.findViewById(android.R.id.text1);
		final int displaynameId = c.getColumnIndexOrThrow(People.DISPLAY_NAME);
		tv.setText(c.getString(displaynameId));
	}
}