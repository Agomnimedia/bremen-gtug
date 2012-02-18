package org.gtugs.bremen.multilabyrinth.scene.api;

public class StartPointElement extends Element{

	private final float[] positions;
	
	public StartPointElement(final float pX, final float pY){
		this.positions = new float[2];
		this.positions[0] = pX;
		this.positions[1] = pY;
	}
	
	@Override
	public ElementKind getElementKind() {
		return ElementKind.START_POINT;
	}

	@Override
	public float[] getPositions() {
		return this.positions;
	}
}