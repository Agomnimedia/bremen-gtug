package org.gtugs.bremen.multilabyrinth.scene.elements;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class WallElement extends Element{

	private final float[] positions;
	
	public WallElement(final float pX1, final float pY1, final float width, final float height){
		this.positions = new float[4];
		this.positions[0] = pX1;
		this.positions[1] = pY1;
		this.positions[2] = width;
		this.positions[3] = height;
	}

	public WallElement(JSONObject json) {
		this.positions = new float[4];
		try {
			this.positions[0] = Float.parseFloat(String.valueOf(json.get("positions0")));
			this.positions[1] = Float.parseFloat(String.valueOf(json.get("positions1")));
			this.positions[2] = Float.parseFloat(String.valueOf(json.get("positions2")));
			this.positions[3] = Float.parseFloat(String.valueOf(json.get("positions3")));
		} catch (JSONException e) {
			Log.e(this.getClass().getSimpleName(), "JSONException occured: " + e.getMessage());
		}
	}

	@Override
	public ElementKind getElementKind() {
		return ElementKind.WALL;
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
			json.put("positions2", String.valueOf(this.positions[2]));
			json.put("positions3", String.valueOf(this.positions[3]));
			json.put("kind", this.getElementKind().toString());
		}catch(JSONException e){
			Log.e("BallElement", "JSONException occured: " + e.getMessage());
		}
		return json;
	}
}