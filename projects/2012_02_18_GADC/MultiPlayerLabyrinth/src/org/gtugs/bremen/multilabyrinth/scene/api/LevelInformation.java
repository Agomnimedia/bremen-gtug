package org.gtugs.bremen.multilabyrinth.scene.api;

import java.util.ArrayList;
import java.util.List;

import org.gtugs.bremen.multilabyrinth.scene.elements.BallElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.Element;
import org.gtugs.bremen.multilabyrinth.scene.elements.ElementKind;
import org.gtugs.bremen.multilabyrinth.scene.elements.EndPointElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.PortalElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.StartPointElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.TrapElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.WallElement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LevelInformation {
	
	private String ipAddress = null;

	private final List<Element> elementList;
	
	private float[] resetPoint;
	
	public LevelInformation(List<Element> elementList, final float[] resetPoint){
		this.elementList = elementList;
		this.setResetPoint(resetPoint);
	}

	public LevelInformation(final JSONObject jsonObject) {
		this.elementList = new ArrayList<Element>();
		this.resetPoint = new float[2];
		try {
			final JSONArray array = jsonObject.getJSONArray("elementList");
			for(int i=0; i<array.length(); i++){
				this.addElement(array.getJSONObject(i));
			}
			if(jsonObject.has("ipAddress"))
				this.ipAddress = jsonObject.getString("ipAddress");
			this.resetPoint[0] = Float.parseFloat((String)jsonObject.get("resetPoint0"));
			this.resetPoint[1] = Float.parseFloat((String)jsonObject.get("resetPoint1"));
		} catch (JSONException e) {
			Log.e("LevelInformation", "JSONException occured: " + e.getMessage());
		}
	}
	
	private void addElement(final JSONObject json){
		ElementKind kind = null;
		try{
			kind = ElementKind.findElementKind(json.getString("kind"));
		}catch(JSONException e){
			Log.e("LevelInformation", "JSONException occured: " + e.getMessage());
		}
		switch(kind){
		case BALL:
			this.elementList.add(new BallElement(json));
			break;
		case END_POINT:
			this.elementList.add(new EndPointElement(json));
			break;
		case PORTAL:
			this.elementList.add(new PortalElement(json));
			break;
		case START_POINT:
			this.elementList.add(new StartPointElement(json));
			break;
		case TRAP:
			this.elementList.add(new TrapElement(json));
			break;
		case WALL:
			this.elementList.add(new WallElement(json));
			break;
			default:
			Log.d("LevelInformation", "Unknown ElementKind");	
		}
	}

	/**
	 * @return the elementList
	 */
	public List<Element> getElementList() {
		return elementList;
	}

	/**
	 * @return the resetPoint
	 */
	public float[] getResetPoint() {
		return resetPoint;
	}

	/**
	 * @param resetPoint the resetPoint to set
	 */
	public void setResetPoint(float[] resetPoint) {
		this.resetPoint = resetPoint;
	}
	
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public JSONObject toJSON(){
		final JSONObject json = new JSONObject();
		final JSONArray array = new JSONArray();
		for(int i=0; i<this.elementList.size(); i++){
			array.put(this.elementList.get(i).toJSON());
		}
		try{
		json.put("elementList", array);
		json.put("ipAddress", this.ipAddress);
		json.put("resetPoint0", String.valueOf(resetPoint[0]));
		json.put("resetPoint1", String.valueOf(resetPoint[1]));
		}catch(JSONException je){
			Log.e("LevelInformation", "JSONException occured: " + je.getMessage());
		}
		return json;
	}
}