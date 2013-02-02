package de.gdgbremen.mapsapibattle.android.fragments;

import de.gdgbremen.mapsapibattle.android.ControlActions;
import de.gdgbremen.mapsapibattle.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ControlsFragment extends Fragment {
	
	public boolean controlsChecked;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.controls, container, false);

		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 6.0f);
		view.setLayoutParams(param);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		final Button upButton = (Button) getView().findViewById(R.id.up);
		upButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions)getActivity()).tiltUp();
			}
		});
		
		final Button downButton = (Button) getView().findViewById(R.id.down);
		downButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions)getActivity()).tiltDown();
			}
		});
	}
}
