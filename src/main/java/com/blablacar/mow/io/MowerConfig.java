package com.blablacar.mow.io;

/**
 * Simple POJO representing how a mower is defined in configuration file.
 * 
 * @author laurent
 *
 */
public class MowerConfig {

	private int X;
	private int Y;
	private String orientation;
	private String commands;
	
	public int getX() {
		return X;
	}
	public void setX(final int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(final int y) {
		Y = y;
	}
	public String getCommands() {
		return commands;
	}
	public void setCommands(final String commands) {
		this.commands = commands;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(final String orientation) {
		this.orientation = orientation;
	}
}
