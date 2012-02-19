package org.gtugs.bremen.multilabyrinth.scene.elements;

import org.json.JSONObject;


public abstract class Element {

	public abstract ElementKind getElementKind();
	
	
	public abstract float[] getPositions();
	
	public abstract JSONObject toJSON();
	// TODO add getter for further information
}
