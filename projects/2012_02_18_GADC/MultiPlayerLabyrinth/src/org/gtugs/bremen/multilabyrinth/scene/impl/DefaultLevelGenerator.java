package org.gtugs.bremen.multilabyrinth.scene.impl;

import java.util.ArrayList;
import java.util.List;

import org.gtugs.bremen.multilabyrinth.scene.api.LevelGenerator;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.gtugs.bremen.multilabyrinth.scene.elements.BallElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.Element;
import org.gtugs.bremen.multilabyrinth.scene.elements.EndPointElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.PortalElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.StartPointElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.TrapElement;
import org.gtugs.bremen.multilabyrinth.scene.elements.WallElement;

public class DefaultLevelGenerator implements LevelGenerator{

	private final List<LevelInformation> informations;
	
	public DefaultLevelGenerator(final int number){
		this.informations = new ArrayList<LevelInformation>();
		switch(number){
		// 1-10 single
		case 1:
			this.mode1();
			break;
		case 2:
			this.mode2();
		//11-20 multi 2players
		case 11:
			this.mode11();
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
	
	private void mode1(){
		final List<Element> elementList = new ArrayList<Element>();
		// left wall
		elementList.add(new WallElement(5, 5, 20, 480 - 10));
		// right wall
		elementList.add(new WallElement(720 - 30, 5, 20, 480-10));
		// top wall
		elementList.add(new WallElement(5, 5, 720-25, 20));
		// bottom wall
		elementList.add(new WallElement(5, 480 - 25, 720-25, 20));
		// start point 
		elementList.add(new StartPointElement(80, 80));
		// end point
		elementList.add(new EndPointElement(720-80, 480-80));
		// trap 1
		elementList.add(new TrapElement(135, 135));
		// trap 2
		elementList.add(new TrapElement(135, 75));
		// portal
		elementList.add(new PortalElement("mode1",640, 80, 640, 400));
		this.informations.add(new LevelInformation(elementList, new float[]{80, 80}));
	}
	
	private void mode2() {
		final List<Element> elementList = new ArrayList<Element>();
		// left wall
		elementList.add(new WallElement(5, 5, 20, 480 - 10));
		// right wall
		elementList.add(new WallElement(720 - 30, 5, 20, 480-10));
		// top wall
		elementList.add(new WallElement(5, 5, 720-25, 20));
		// bottom wall
		elementList.add(new WallElement(5, 480 - 25, 720-25, 20));
		// start point 
		elementList.add(new StartPointElement(80, 80));
		// trap 2
		elementList.add(new TrapElement(235, 245));
		// end point
		elementList.add(new EndPointElement(720-80, 480-80));
		// portal 1
		elementList.add(new PortalElement("mode2",640, 80, 640, 120));
		// portal 2
		elementList.add(new PortalElement("mode2",230, 80, 370, 120));
		
		elementList.add(new BallElement(720-80, 480-80));
		this.informations.add(new LevelInformation(elementList, new float[]{80, 80}));
	}
	
	private void mode11(){
		final List<Element> elementList = new ArrayList<Element>();
		// left wall
		elementList.add(new WallElement(5, 5, 20, 480 - 10));
		// portal instead right wall
		elementList.add(new PortalElement("mode11",720 - 30, 5, 720 - 30, 480-5));
		// top wall
		elementList.add(new WallElement(5, 5, 720-25, 20));
		// bottom wall
		elementList.add(new WallElement(5, 480 - 25, 720-25, 20));
		// start point 
		elementList.add(new StartPointElement(80, 80));
		this.informations.add(new LevelInformation(elementList, new float[]{80, 80}));
		final List<Element> elementList2 = new ArrayList<Element>();
		// portal instead left wall
		elementList2.add(new PortalElement("mode11",5, 5, 5, 480 - 5));
		// right wall
		elementList2.add(new WallElement(720 - 30, 5, 20, 480-10));
		// top wall
		elementList2.add(new WallElement(5, 5, 720-25, 20));
		// bottom wall
		elementList2.add(new WallElement(5, 480 - 25, 720-25, 20));
		// end point 
		elementList2.add(new EndPointElement(720-80, 480-80));
		this.informations.add(new LevelInformation(elementList2, new float[]{15,200}));
		
	}
	
	@Override
	public List<LevelInformation> getLevelinformation() {
		return this.informations;
	}
}