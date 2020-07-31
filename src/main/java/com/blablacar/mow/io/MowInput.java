package com.blablacar.mow.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO representing the global configuration file, with lawn and mowers.
 * @author laurent
 *
 */
public class MowInput {

	private int lawnX;
	private int lawnY;
	private List<MowerConfig> mowers = new ArrayList<>();

	public int getLawnX() {
		return lawnX;
	}

	public void setLawnX(int lawnX) {
		this.lawnX = lawnX;
	}

	public int getLawnY() {
		return lawnY;
	}

	public void setLawnY(int lawnY) {
		this.lawnY = lawnY;
	}

	public List<MowerConfig> getMowers() {
		return mowers;
	}

	public void setMowers(List<MowerConfig> mowers) {
		this.mowers = mowers;
	}
}
