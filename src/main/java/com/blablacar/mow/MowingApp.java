package com.blablacar.mow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blablacar.mow.io.MowInput;
import com.blablacar.mow.io.MowParser;
import com.blablacar.mow.service.MowProcessor;
import com.blablacar.mow.service.MowProcessorFactory;

/**
 * Mowing application main.
 * <ul>
 * <li>check arguments</li>
 * <li>call the input file parser</li>
 * <li>launch the {@code MowProcessor}</li>
 * </ul>
 * @author laurent
 *
 */
@SpringBootApplication
public class MowingApp {

	private static Logger logger = LoggerFactory.getLogger(MowingApp.class);
	
	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MowingApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
		if(args.length != 1) {
			logger.error("Invalid command line");
			printUsage();
		} else {
			File f = new File(args[0]);
			if(!f.exists()) {
				logger.error("input file '{}' does not exist", args[0]);
				printUsage();
			} else {
				logger.info("Input file found: {}", f.getAbsolutePath());
				FileInputStream fis = null;
				try{
					fis = new FileInputStream(f);
					MowParser p = new MowParser();
					MowInput cfg = p.parse(fis);
					MowProcessor processor = MowProcessorFactory.build(cfg);
					processor.mow();
				} catch (FileNotFoundException e) {
					logger.error("File {} cannot be found", f.getAbsoluteFile());
				} finally {
					try {
						if(fis != null){
							fis.close();
						}
					} catch (IOException e) {
						logger.error("Error closing FileInputStream: {}", f.getAbsoluteFile());
					}
				}
			}
			
		}
	}

	private static void printUsage(){
		System.err.println("Usage:\n\trun.sh (or run.bat) /path/to/file.txt");
	}
}
