package org.gtugs.bremen.multilabyrinth.scene.impl;

import java.util.ArrayList;
import java.util.List;

import org.gtugs.bremen.multilabyrinth.scene.api.Element;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.api.WallElement;

public class DefaultLevelGenerator implements LevelGenerator{

	private final List<LevelInformation> informations;
	
	public DefaultLevelGenerator(final int number){
		this.informations = new ArrayList<LevelInformation>();
		switch(number){
		// 1-10 single
		case 1:
			final List<Element> elementList = new ArrayList<Element>();
			// left wall
			elementList.add(new WallElement(15, 15, 15, 480 - 15));
			// right wall
			elementList.add(new WallElement(720 - 15, 15, 720-15, 480-15));
			// top wall
			elementList.add(new WallElement(5, 15, 720-5, 15));
			// bottom wall
			elementList.add(new WallElement(5, 480 - 15, 720-5, 480-15));
			this.informations.add(new LevelInformation(elementList));
			break;
		//11-20 multi 2players
		case 11:
			
			break;
		// 21-30 multi 3players
		case 21:
			
			break;
		// 31-30 multi 4 players
		case 31:
			
			break;
		default:
			
		}
	}
	
	@Override
	public List<LevelInformation> getLevelinformation() {
		return this.informations;
	}

}
