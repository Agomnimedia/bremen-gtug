package org.gtugs.bremen.multilabyrinth.scene.elements;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class StartPointElement extends Element{

	private final float[] positions;
	
	public StartPointElement(final float pX, final float pY){
		this.positions = new float[2];
		this.positions[0] = pX;
		this.positions[1] = pY;
	}
	
	public StartPointElement(JSONObject json) {
		this.positions = new float[2];
		try {
			this.positions[0] = Float.parseFloat(String.valueOf(json.get("positions0")));
			this.positions[1] = Float.parseFloat(String.valueOf(json.get("positions1")));
		} catch (JSONException e) {
			Log.e(this.getClass().getSimpleName(), "JSONException occured: " + e.getMessage());
		}
	}

	@Override
	public ElementKind getElementKind() {
		return ElementKind.START_POINT;
	}

	@Override
	public float[] getPositions() {
		return this.positions;
	}
	
	@Override
	public JSONObject toJSON() {
		final JSONObject json = new JSONObject();
		try{
			json.put("positions0", String.valueOf(this.positions[0]));
			json.put("positions1", String.valueOf(this.positions[1]));
			json.put("kind", this.getElementKind().toString());
		}catch(JSONException e){
			Log.e("BallElement", "JSONException occured: " + e.getMessage());
		}
		return json;
	}
}