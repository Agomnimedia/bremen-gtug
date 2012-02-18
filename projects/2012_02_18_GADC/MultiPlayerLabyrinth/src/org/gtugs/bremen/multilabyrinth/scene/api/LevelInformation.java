package org.gtugs.bremen.multilabyrinth.scene.api;

import java.util.List;

import org.gtugs.bremen.multilabyrinth.scene.elements.Element;

public class LevelInformation {

	private final List<Element> elementList;
	
	private float[] resetPoint;
	
	public LevelInformation(List<Element> elementList, final float[] resetPoint){
		this.elementList = elementList;
		this.setResetPoint(resetPoint);
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
}
