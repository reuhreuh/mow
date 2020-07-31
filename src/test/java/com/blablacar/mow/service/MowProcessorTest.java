package com.blablacar.mow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.blablacar.mow.model.Command;
import com.blablacar.mow.model.Coordinates;
import com.blablacar.mow.model.Lawn;
import com.blablacar.mow.model.Mower;
import com.blablacar.mow.model.Orientation;
import com.blablacar.mow.model.Position;

/**
 * Test the mowing logic
 * 
 * @author laurent
 */
public class MowProcessorTest {

	private MowProcessor mp = null;

	/**
	 * Test the BlaBlaCar example 
	 * <pre>
	 * 5 5
	 * 1 2 N
	 * LFLFLFLFF
	 * 3 3 E
	 * FFRFFRFRRF
	 * </pre>
	 * Expected result:
	 * <pre>
	 * 1 3 N
	 * 5 1 E
	 * </pre>
	*/
	@Test
	public void testExample() {
		Lawn lawn = new Lawn(5 + 1, 5 + 1);
		List<Mower> mowers = new ArrayList<>();
		mowers.add(buildMover("Mower #1", 1, 2, Orientation.NORTH, Command.LEFT, Command.FORWARD, Command.LEFT, Command.FORWARD, Command.LEFT, Command.FORWARD, Command.LEFT, Command.FORWARD, Command.FORWARD));
		mowers.add(buildMover("Mower #2", 3, 3, Orientation.EAST, Command.FORWARD, Command.FORWARD, Command.RIGHT, Command.FORWARD, Command.FORWARD, Command.RIGHT, Command.FORWARD, Command.RIGHT, Command.RIGHT, Command.FORWARD));

		// Launch mowing
		mp = new MowProcessor(lawn, mowers);
		mp.mow();

		// Test mow position
		assertEquals(1, mp.getMowers().get(0).getPosition().getCoordinates().getX());
		assertEquals(3, mp.getMowers().get(0).getPosition().getCoordinates().getY());
		assertEquals(Orientation.NORTH, mp.getMowers().get(0).getPosition().getOrientation());

		assertEquals(5, mp.getMowers().get(1).getPosition().getCoordinates().getX());
		assertEquals(1, mp.getMowers().get(1).getPosition().getCoordinates().getY());
		assertEquals(Orientation.EAST, mp.getMowers().get(1).getPosition().getOrientation());
	}

	
	/**
	 * Test a Mower going out of Lawn
	 * <pre>
	 * 5 5
	 * 3 2 E
	 * FFFRFF
	 * </pre>
	 * Expected result
	 * <pre>
	 * 5 0 E
	 * </pre>
	 */
	@Test
	public void testOutOfBound(){
		Lawn lawn = new Lawn(5 + 1, 5 + 1);
		List<Mower> mowers = new ArrayList<>();
		mowers.add(buildMover("Mower #1", 3, 2, Orientation.EAST, Command.FORWARD, Command.FORWARD, Command.FORWARD, Command.RIGHT, Command.FORWARD, Command.FORWARD));

		// Launch mowing
		mp = new MowProcessor(lawn, mowers);
		mp.mow();

		// Test mow position
		assertEquals(5, mp.getMowers().get(0).getPosition().getCoordinates().getX());
		assertEquals(0, mp.getMowers().get(0).getPosition().getCoordinates().getY());
		assertEquals(Orientation.SOUTH, mp.getMowers().get(0).getPosition().getOrientation());
	}
	
	/**
	 * Test concurrent access to same cell
	 * <pre>
	 * 5 5
	 * 1 3 E
	 * F
	 * 3 3 W
	 * F
	 * </pre>
	 * Expected result:
	 * <pre>
	 * 2 3 E
	 * 3 3 W
	 * </pre>
	*/
	@Test
	public void testConcurrent() {
		Lawn lawn = new Lawn(5 + 1, 5 + 1);
		List<Mower> mowers = new ArrayList<>();
		mowers.add(buildMover("Mower #1", 1, 3, Orientation.EAST, Command.FORWARD));
		mowers.add(buildMover("Mower #2", 3, 3, Orientation.WEST, Command.FORWARD));

		// Launch mowing
		mp = new MowProcessor(lawn, mowers);
		mp.mow();

		// Test mow position
		assertEquals(2, mp.getMowers().get(0).getPosition().getCoordinates().getX());
		assertEquals(3, mp.getMowers().get(0).getPosition().getCoordinates().getY());
		assertEquals(Orientation.EAST, mp.getMowers().get(0).getPosition().getOrientation());

		assertEquals(3, mp.getMowers().get(1).getPosition().getCoordinates().getX());
		assertEquals(3, mp.getMowers().get(1).getPosition().getCoordinates().getY());
		assertEquals(Orientation.WEST, mp.getMowers().get(1).getPosition().getOrientation());
	}
	
	private Mower buildMover(String name, int x, int y, Orientation o, Command... commands) {
		Coordinates xy = new Coordinates(x, y);
		Position p = new Position(xy, o);
		Mower m = new Mower(name, p, new LinkedList<>(Arrays.asList(commands)));
		return m;
	}

}
