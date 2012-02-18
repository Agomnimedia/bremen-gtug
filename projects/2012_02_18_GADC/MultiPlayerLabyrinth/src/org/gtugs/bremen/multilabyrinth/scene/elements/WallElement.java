package org.gtugs.bremen.multilabyrinth.scene.elements;


public class WallElement extends Element{

	private final float[] positions;
	
	public WallElement(final float pX1, final float pY1, final float pX2, final float pY2){
		this.positions = new float[4];
		this.positions[0] = pX1;
		this.positions[1] = pY1;
		this.positions[2] = pX2;
		this.positions[3] = pY2;
	}

	@Override
	public ElementKind getElementKind() {
		return ElementKind.WALL;
	}

	@Override
	public float[] getPositions() {
		return this.positions;
	}
}