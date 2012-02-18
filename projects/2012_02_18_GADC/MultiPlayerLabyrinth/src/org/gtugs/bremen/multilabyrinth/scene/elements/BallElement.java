package org.gtugs.bremen.multilabyrinth.scene.elements;

public class BallElement extends Element{

	private final float[] positions;
	
	public BallElement(final float pX, final float pY){
		this.positions = new float[2];
		this.positions[0] = pX;
		this.positions[1] = pY;
	}
	
	@Override
	public ElementKind getElementKind() {
		return ElementKind.BALL;
	}

	@Override
	public float[] getPositions() {
		return this.positions;
	}
}