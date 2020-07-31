package com.blablacar.mow.model;

/**
 * POJO modeling a Lawn. A Lawn is a grid of {@link Area}.
 * @author laurent
 *
 */
public class Lawn {
	
	private Area[][] grid;
	
	public Lawn(final int length, final int height) {
		grid = new Area[length][height];
		for(int l=0; l<length; l++) {
			for(int h=0; h<height; h++){
				grid[l][h] = new Area(new Coordinates(l, h), false);
			}
		}
	}

	public Area[][] getGrid() {
		return grid;
	}
	
	public Area getArea(final int x, final int y) {
		return this.grid[x][y];
	}

	public void setGrid(Area[][] grid) {
		this.grid = grid;
	}
	
	public int getHeight() {
		if(this.grid.length > 0) {
			return this.grid[0].length;
		}
		return 0;
	}
	
	public int getLength() {
		return this.grid.length;
	}
}
