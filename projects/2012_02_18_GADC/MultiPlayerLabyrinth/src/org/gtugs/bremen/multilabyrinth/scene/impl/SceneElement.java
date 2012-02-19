package org.gtugs.bremen.multilabyrinth.scene.impl;

import org.andengine.entity.shape.Shape;
import org.gtugs.bremen.multilabyrinth.scene.elements.ElementKind;

public class SceneElement {
	
	private final Shape shape;
	
	private final ElementKind kind;

	public SceneElement(final Shape shape, final ElementKind kind){
		this.shape = shape;
		this.kind=kind;
	}

	/**
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * @return the kind
	 */
	public ElementKind getKind() {
		return kind;
	}
}
