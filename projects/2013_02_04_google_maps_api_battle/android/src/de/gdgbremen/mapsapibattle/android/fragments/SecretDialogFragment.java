package de.gdgbremen.mapsapibattle.android.fragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import de.gdgbremen.mapsapibattle.android.ControlActions;
import de.gdgbremen.mapsapibattle.android.R;

public class SecretDialogFragment extends DialogFragment{

	
	
	public SecretDialogFragment() {
		// Empty constructor required for DialogFragment
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secret_dialog, container);
        
        getDialog().setTitle("Geheimer Dialog");
        
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		
		final SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
        final boolean additionalControls = pref.getBoolean("additionalControls", false);
		final boolean traffic = pref.getBoolean("traffic", false);
		
		addCheckboxListener(additionalControls, traffic);
		addButtonListener();
	}

	private void addButtonListener() {
		final ImageButton navBremen = (ImageButton) this.getView().findViewById(R.id.navigateBremen);
		navBremen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions) getActivity()).animateToBremen();
				dismiss();
			}
		});
		
		final ImageButton navMunich = (ImageButton) this.getView().findViewById(R.id.navigateMunich);
		navMunich.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions) getActivity()).animateToMunich();
				dismiss();
			}
		});
		
		final ImageButton navNewYork = (ImageButton) this.getView().findViewById(R.id.navigateNewYork);
		navNewYork.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ControlActions) getActivity()).animateToNewYork();
				dismiss();
			}
		});
	}

	private void addCheckboxListener(boolean additionalControlsShown, boolean traffic) {
		final CheckBox additionalControls = (CheckBox) this.getView().findViewById(
				R.id.additionalControls);
		
		additionalControls.setChecked(additionalControlsShown);
		
		additionalControls.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					((ControlActions) getActivity()).show();
				} else {
					((ControlActions) getActivity()).hide();
				}
				final Editor editor = PreferenceManager
						.getDefaultSharedPreferences(getActivity()).edit();
				editor.putBoolean("additionalControls", isChecked);
				editor.commit();

			}
		});
		
		final CheckBox showTraffic = (CheckBox) this.getView().findViewById(R.id.showTraffic);
		showTraffic.setChecked(traffic);
		
		showTraffic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((ControlActions) getActivity()).traffic(isChecked);
				final Editor editor = PreferenceManager
						.getDefaultSharedPreferences(getActivity()).edit();
				editor.putBoolean("traffic", isChecked);
				editor.commit();
			}
		});
	}
}
