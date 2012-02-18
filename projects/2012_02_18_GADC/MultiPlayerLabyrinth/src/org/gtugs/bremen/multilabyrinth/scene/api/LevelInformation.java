package org.gtugs.bremen.multilabyrinth.scene.api;

import java.util.List;

public class LevelInformation {

	private final List<Element> elementList;
	
	public LevelInformation(List<Element> elementList){
		this.elementList = elementList;
	}

	/**
	 * @return the elementList
	 */
	public List<Element> getElementList() {
		return elementList;
	}
}
