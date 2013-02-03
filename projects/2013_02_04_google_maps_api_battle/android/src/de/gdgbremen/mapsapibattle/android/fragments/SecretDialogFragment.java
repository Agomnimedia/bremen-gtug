package de.gdgbremen.mapsapibattle.android.fragments;

import de.gdgbremen.mapsapibattle.android.ControlActions;
import de.gdgbremen.mapsapibattle.android.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
		
		addCheckboxListener();
		addButtonListener();
	}

	private void addButtonListener() {
		final Button navBremen = (Button) this.getView().findViewById(R.id.navigateBremen);
		navBremen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions) getActivity()).animateToBremen();
				dismiss();
			}
		});
		
		final Button navMunich = (Button) this.getView().findViewById(R.id.navigateMunich);
		navMunich.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions) getActivity()).animateToMunich();
				dismiss();
			}
		});
		
		final Button navNewYork = (Button) this.getView().findViewById(R.id.navigateNewYork);
		navNewYork.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions) getActivity()).animateToNewYork();
				dismiss();
			}
		});
	}

	private void addCheckboxListener() {
		final CheckBox additionalControls = (CheckBox) this.getView().findViewById(
				R.id.additionalControls);
		
		// TODO change this
		additionalControls.setChecked(false);
		
		additionalControls.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					((ControlActions) getActivity()).show();
				} else {
					((ControlActions) getActivity()).hide();
				}

			}
		});
		
		final CheckBox showTraffic = (CheckBox) this.getView().findViewById(R.id.showTraffic);
		// TODO change this
		showTraffic.setChecked(false);
		
		showTraffic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((ControlActions) getActivity()).traffic(isChecked);
				
			}
		});
	}
}
