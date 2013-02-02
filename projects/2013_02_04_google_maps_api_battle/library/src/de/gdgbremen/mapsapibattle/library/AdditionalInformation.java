package de.gdgbremen.mapsapibattle.library;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public final class AdditionalInformation {

	public String id;
	
	public String abkuerzung;
	public String strasse;
	public String hausnummer;
	public String lage;
	public String adresszusatz;
	public String postfach;
	public String ortsteil;
	public String plzPostfach;
	public String plz;
	public String ort;
	public String email;
	public String telefon;
	public String fax;
	public String homepage;
	public String oeffnungszeiten;
	public String latitude;
	public String longitude;
	public String beschreibung;
	
	public AdditionalInformation(){
		// nothing to implement here
	}
	
	public AdditionalInformation(final String id){
		this.id = id;
	}
	
	@Override
	public String toString(){
		final JSONObject json = new JSONObject();
		try {
			if(homepage!=null)
				json.put("homepage", homepage);
			if(oeffnungszeiten!=null)
				json.put("oeffnungszeiten", oeffnungszeiten);
			if(beschreibung!=null)
				json.put("beschreibung", beschreibung);
		} catch (JSONException e) {
			Log.e(AdditionalInformation.class.getSimpleName(), "JSONException occured: " + e.getMessage());
		}
		return json.toString();
	}
}