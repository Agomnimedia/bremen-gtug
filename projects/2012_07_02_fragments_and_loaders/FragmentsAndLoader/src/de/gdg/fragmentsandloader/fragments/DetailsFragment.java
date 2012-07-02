package de.gdg.fragmentsandloader.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class DetailsFragment extends Fragment implements LoaderCallbacks<Cursor> {

	public static final String CONTACT_ID = "contactId";
	private int mContactID;

	public DetailsFragment newInstance(final int contactId) {
		DetailsFragment f = new DetailsFragment();
		Bundle args = new Bundle();
		args.putInt(CONTACT_ID, contactId);
		f.setArguments(args);
		return f;
	}

	public int getShownIndex() {
		return mContactID;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(getActivity().getApplicationContext(), People.CONTENT_URI , projection, "WHERE " + People._ID + " = ?", , null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
	}

}
