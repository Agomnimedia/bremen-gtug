package de.gdgbremen.mapsapibattle.android.fragments;

import de.gdgbremen.mapsapibattle.android.ControlActions;
import de.gdgbremen.mapsapibattle.android.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SecretDialogFragment extends DialogFragment{

	public SecretDialogFragment() {
		// Empty constructor required for DialogFragment
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secret_dialog, container);
        
        getDialog().setTitle("Geheimer Dialog");
        
        final SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		// TODO load additional controls
        
        
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		
		final CheckBox additionalControls = (CheckBox)this.getView().findViewById(R.id.additionalControls);
		additionalControls.setChecked(false);
		
		final CheckBox checkBox = (CheckBox) this.getView().findViewById(
				R.id.additionalControls);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					((ControlActions) getActivity()).show();
				} else {
					((ControlActions) getActivity()).hide();
				}

			}
		});
	}
}
