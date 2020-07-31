package com.blablacar.mow.model;

import com.blablacar.mow.service.MowProcessor;

/**
 * Simple Enum representing the mower orientation.
 * @author laurent
 *
 */
public enum Orientation {

	/** 
	* order is important here
	* used for rotation command (see {@link MowProcessor#rotate()})
	*/
	NORTH('N'), // 0
	EAST('E'),  // 1
	SOUTH('S'), // 2
	WEST('W');  // 3

	private char code;

	private Orientation(final char code) {
		this.code = code;
	}

	public char getCode() {
		return this.code;
	}

	public static Orientation valueOfCode(final char code) {
		switch (code) {
		case 'N':
			return NORTH;
		case 'S':
			return SOUTH;
		case 'W':
			return WEST;
		case 'E':
			return EAST;
		default:
			throw new IllegalArgumentException("Unknown orientation code: " + code);
		}
	}
}
