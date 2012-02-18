package org.gtugs.bremen.multilabyrinth.scene.api;

public abstract class Element {

	public abstract ElementKind getElementKind();
	
	
	public abstract float[] getPositions();
	// TODO add getter for further information
}
