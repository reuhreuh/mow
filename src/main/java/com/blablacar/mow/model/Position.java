package com.blablacar.mow.model;

/**
 * POJO handling mower coordinates and orientation
 * @author laurent
 *
 */
public class Position {
	
	private Coordinates coordinates;
	private Orientation orientation;
	
	public Position(Coordinates coordinates, Orientation orientation) {
		super();
		this.coordinates = coordinates;
		this.orientation = orientation;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(final Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return String.format("%d %d %s", coordinates.getX(), coordinates.getY(), orientation.getCode());
	}
}
