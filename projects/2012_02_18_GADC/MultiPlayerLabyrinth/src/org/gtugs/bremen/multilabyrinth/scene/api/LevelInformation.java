package org.gtugs.bremen.multilabyrinth.scene.api;

import java.util.List;

import org.gtugs.bremen.multilabyrinth.scene.elements.Element;

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
