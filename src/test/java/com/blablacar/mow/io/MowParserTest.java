package com.blablacar.mow.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test input file parsing
 * @author laurent
 *
 */
@RunWith(SpringRunner.class)
public class MowParserTest {

	private MowParser parser;
	
	@Test 
	public void testBasicInput(){
		parser = new MowParser();
		String example = "5 5\n1 2 N\nLFLFLFLFF\n3 3 E\nFFRFFRFRRF";
		InputStream is = new ByteArrayInputStream(example.getBytes(Charset.forName("UTF-8")));
		MowInput res = parser.parse(is);
		// lawn
		assertEquals(5, res.getLawnX());
		assertEquals(5, res.getLawnY());
		// mowers
		assertEquals(2, res.getMowers().size());
		// first mower
		assertEquals(1, res.getMowers().get(0).getX());
		assertEquals(2, res.getMowers().get(0).getY());
		assertEquals("N", res.getMowers().get(0).getOrientation());
		assertEquals("LFLFLFLFF", res.getMowers().get(0).getCommands());
		// second mower
		assertEquals(3, res.getMowers().get(1).getX());
		assertEquals(3, res.getMowers().get(1).getY());
		assertEquals("E", res.getMowers().get(1).getOrientation());
		assertEquals("FFRFFRFRRF", res.getMowers().get(1).getCommands());
	}
}
