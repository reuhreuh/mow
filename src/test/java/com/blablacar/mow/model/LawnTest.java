package com.blablacar.mow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.blablacar.mow.model.Area;
import com.blablacar.mow.model.Command;
import com.blablacar.mow.model.Lawn;

/**
 * Testing grid indexes
 * 
 * @author laurent
 *
 */
@RunWith(SpringRunner.class)
public class LawnTest {
	
	@Test
	public void testLawnGrid() {
		Lawn l = new Lawn(4, 3);
		
		assertEquals(4, l.getLength());
		assertEquals(3, l.getHeight());
		
		Area a10 = l.getArea(1, 0); 
		assertEquals(1, a10.getCoordinates().getX());
		assertEquals(0, a10.getCoordinates().getY());

		Area a32 = l.getArea(3, 2); 
		assertEquals(3, a32.getCoordinates().getX());
		assertEquals(2, a32.getCoordinates().getY());
		
		Queue<Command> commands = new LinkedList<>();
		commands.add(Command.FORWARD);
		commands.add(Command.RIGHT);
		System.out.println(commands.poll());
		System.out.println(commands.poll());
	}
}
