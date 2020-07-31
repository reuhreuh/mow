package com.blablacar.mow.model;

/**
 * Basic POJO with X and Y coordinates, for positioning Mower on the Lawn.
 * @author laurent
 *
 */
public class Coordinates {
	
	private int x;
	private int y;

	public Coordinates(final int x, final int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
