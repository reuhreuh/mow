package com.blablacar.mow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.blablacar.mow.io.MowInput;
import com.blablacar.mow.io.MowerConfig;
import com.blablacar.mow.model.Command;
import com.blablacar.mow.model.Orientation;
import com.blablacar.mow.service.MowProcessor;
import com.blablacar.mow.service.MowProcessorFactory;

/**
 * Test the {@code MowProcessor} building.
 * @author laurent
 *
 */
public class MowProcessorFactoryTest {
	
	@Test
	public void testMPbuilding(){
		// considering the input parsing result:
		MowInput input = new MowInput();
		input.setLawnX(5);
		input.setLawnY(5);
		input.setMowers(new ArrayList<>());
		MowerConfig mc1 = new MowerConfig();
		mc1.setX(1);
		mc1.setY(2);
		mc1.setOrientation("N");
		mc1.setCommands("LFLFLFLFF");
		input.getMowers().add(mc1);
		MowerConfig mc2 = new MowerConfig();
		mc2.setX(3);
		mc2.setY(3);
		mc2.setOrientation("E");
		mc2.setCommands("FFRFFRFRRF");
		input.getMowers().add(mc2);
		
		// create the MowProcessor
		MowProcessor mp = MowProcessorFactory.build(input);
		// test
		// the input file length defines the last indexes (5)
		// since the grid starts with '0', the length is '6'
		assertEquals(input.getLawnY() + 1, mp.getLawn().getHeight());
		assertEquals(input.getLawnX() + 1, mp.getLawn().getLength());
		assertEquals(input.getMowers().size(), mp.getMowers().size());
		assertEquals(Orientation.NORTH, mp.getMowers().get(0).getPosition().getOrientation());
		assertEquals(1, mp.getMowers().get(0).getPosition().getCoordinates().getX());
		assertEquals(2, mp.getMowers().get(0).getPosition().getCoordinates().getY());
		assertEquals(9, mp.getMowers().get(0).getCommands().size());
		assertEquals(Command.LEFT, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.FORWARD, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.LEFT, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.FORWARD, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.LEFT, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.FORWARD, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.LEFT, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.FORWARD, mp.getMowers().get(0).getCommands().poll());
		assertEquals(Command.FORWARD, mp.getMowers().get(0).getCommands().poll());
	}
}
