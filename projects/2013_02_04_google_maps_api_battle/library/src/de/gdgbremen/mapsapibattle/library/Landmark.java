package de.gdgbremen.mapsapibattle.library;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public final class Landmark {

	public final String name;

	public final String address;

	public AdditionalInformation additionalInformation;

	public Landmark(final String name, final String address,
			final String addInfoId) {
		this.name = name;
		this.address = address;
		if (addInfoId != null) {
			this.additionalInformation = new AdditionalInformation(addInfoId);
		}
	}

	/**
	 * Generates an JSON object with all information to this landmark.
	 * 
	 * @return an JSON object.
	 */
	public JSONObject toJSON() {
		final JSONObject json = new JSONObject();
		try {
			if (name != null) json.put("name", name);
			if (address != null) json.put("address", address);
			if (additionalInformation != null) json.put("additionalInformation",
					additionalInformation.toJSON());
		} catch (JSONException e) {
			Log.e(AdditionalInformation.class.getSimpleName(),
					"JSONException occured: " + e.getMessage());
		}
		return json;
	}

	/**
	 * Creates an JSON formatted string with all information to this landmark.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.toJSON().toString();
	}
}
