package de.gdg.fragmentsandloader.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import de.gdg.fragmentandloaders.utils.ContactAdapter;

public final class ContactListFragment extends android.support.v4.app.ListFragment implements LoaderCallbacks<Cursor> {

	public interface OnContactSelectedListener{
		public void onContactSelected(final int contactId);
	}
	
	
	private static final int[] TO = new int[] {android.R.id.text1};
	private static final String[] FROM = new String[] {People.DISPLAY_NAME};
	private static final int LOADER_ID = 0;
	private static final String TAG = ContactListFragment.class.getName();
	ContactAdapter mAdapter;
	
	OnContactSelectedListener mListener;
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			mListener = (OnContactSelectedListener) activity;
		}catch (ClassCastException exp){
			throw new ClassCastException(activity.toString() + "must implement OnItemSelectedListener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mAdapter = new ContactAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, null,FROM, TO, 0);
		setListAdapter(mAdapter);
		LoaderManager lm = getLoaderManager();
		if(lm != null){
			lm.initLoader(LOADER_ID, null, this);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(getActivity().getApplicationContext(), People.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "Item clicked: " + position);
        mListener.onContactSelected(position);
        
    }
}
