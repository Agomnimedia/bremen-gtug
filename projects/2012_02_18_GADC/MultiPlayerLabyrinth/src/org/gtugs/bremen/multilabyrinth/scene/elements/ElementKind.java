package org.gtugs.bremen.multilabyrinth.scene.elements;

public enum ElementKind {

	BALL,
	
	WALL,
	
	START_POINT,
	
	END_POINT,
	
	TRAP,
	
	PORTAL;
	
	public static ElementKind findElementKind(final String param){
		final ElementKind result;
		if(param.equals("BALL")){
			result = BALL;
		}else if(param.equals("WALL")){
			result = WALL;
		}else if(param.equals("START_POINT")){
			result = START_POINT;
		}else if(param.equals("END_POINT")){
			result = END_POINT;
		}else if(param.equals("TRAP")){
			result = TRAP;
		}else if(param.equals("PORTAL")){
			result = PORTAL;
		}else{
			result = null;
		}
		return result;
	}
}
