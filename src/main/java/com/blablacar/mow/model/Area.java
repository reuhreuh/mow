package com.blablacar.mow.model;

/**
 * Represents a {@link Lawn} grid cell. It has coordinates and a state, 
 * telling if the Area is occupied by a mower.
 * 
 * @author laurent
 *
 */
public class Area {
	
	private Coordinates coordinates;
	private boolean isBusy = false;
	
	public Area(final Coordinates coordinates, final boolean isBusy) {
		super();
		this.coordinates = coordinates;
		this.isBusy = isBusy;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(final boolean isBusy) {
		this.isBusy = isBusy;
	}

	@Override
	public String toString() {
		return String.format("Area (%d,%d),occupied=%b",coordinates.getX(), coordinates.getY(), isBusy);
	}
	
}
