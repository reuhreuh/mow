package com.blablacar.mow.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blablacar.mow.MowingApp;

/**
 * Create a structure ({@link MowInput}) representing the configuration file. 
 * @author laurent
 *
 */
public class MowParser {

	private Logger logger = LoggerFactory.getLogger(MowingApp.class);
	
	/**
	 * Parse the {@link InputStream} line by line to create a POJO representing the configuration.
	 * @param in the input configuration 
	 * @return the configuration abstraction
	 * @throws IllegalArgumentException if unexpected structure is found
	 */
	public MowInput parse(final InputStream in){

		MowInput res = new MowInput();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		boolean firstLine = true;
		boolean newMow = true;
		MowerConfig mowerCfg = null;
		try {
			while ((line = br.readLine()) != null) {
				if(firstLine){
					logger.debug("Parsing first line: {}", line);
					// Reading first line dimensions
					String[] dims = line.split(" ");
					if(dims == null || dims.length != 2) {
						throwIAE("Invalid first line: " + line);
					} else {
						res.setLawnX(Integer.parseInt(dims[0]));
						res.setLawnY(Integer.parseInt(dims[1]));
						firstLine = false;
					}
				} else {
					if(newMow){
						// Reading new mower configuration (position)
						logger.debug("Parsing new mower line: {}", line);
						mowerCfg = new MowerConfig();
						newMow = false;
						String[] pos = line.split(" ");
						if(pos == null || pos.length != 3) {
							throwIAE("Invalid mower position line: " + line);
						}
						mowerCfg.setX(Integer.parseInt(pos[0]));
						mowerCfg.setY(Integer.parseInt(pos[1]));
						mowerCfg.setOrientation(pos[2]);
						
					} else {
						// Reading current mower commands
						logger.debug("Parsing mower commands: {}", line);
						mowerCfg.setCommands(line);
						res.getMowers().add(mowerCfg);
						newMow = true;
					}
				}
			}
		} catch (IOException ioe) {
			throwIAE("Error reading input file", ioe);
		}
		
		return res;
	}
	
	private void throwIAE(final String message) {
		throwIAE(message, null);
	}
	private void throwIAE(final String message, final Throwable cause){
		logger.error(message, cause);
		throw new IllegalArgumentException(message, cause);
	}
}
