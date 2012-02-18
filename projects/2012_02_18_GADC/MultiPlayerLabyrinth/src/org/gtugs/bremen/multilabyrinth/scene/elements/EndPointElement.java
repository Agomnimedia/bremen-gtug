package org.gtugs.bremen.multilabyrinth.scene.elements;

public class EndPointElement extends Element{

	private final float[] positions;
	
	public EndPointElement(final float pX, final float pY){
		this.positions = new float[2];
		this.positions[0] = pX;
		this.positions[1] = pY;
	}
	
	@Override
	public ElementKind getElementKind() {
		return ElementKind.END_POINT;
	}

	@Override
	public float[] getPositions() {
		return this.positions;
	}
}
