/**
 * 
 */
package com.blablacar.mow.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blablacar.mow.io.MowInput;
import com.blablacar.mow.io.MowerConfig;
import com.blablacar.mow.model.Command;
import com.blablacar.mow.model.Coordinates;
import com.blablacar.mow.model.Lawn;
import com.blablacar.mow.model.Mower;
import com.blablacar.mow.model.Orientation;
import com.blablacar.mow.model.Position;

/**
 * Build a {@link MowProcessor} from the input file representation: {@link MowInput}
 * @author laurent
 *
 */
public class MowProcessorFactory {
	
	private static Logger logger = LoggerFactory.getLogger(MowProcessorFactory.class);

	/**
	 * Build the configured and ready to start {@link MowProcessor} from the configuration. 
	 * @param cfg the POJO representing the file configuration
	 * @return the configured processor
	 */
	public static MowProcessor build(MowInput cfg) {
		Lawn lawn = new Lawn(cfg.getLawnX() + 1, cfg.getLawnY() + 1);
		List<Mower> mowers = new ArrayList<>(cfg.getMowers().size());
		int i = 1;
		for (MowerConfig mc : cfg.getMowers()) {
			Coordinates xy = new Coordinates(mc.getX(), mc.getY());
			Orientation orientation = Orientation.valueOfCode(mc.getOrientation().charAt(0));
			Position position = new Position(xy, orientation);
			Queue<Command> commands = createCommandQueue(mc.getCommands());
			Mower m = new Mower("Mower #" + i, position, commands);			
			mowers.add(m);
			logger.info("{} initialized", m.toString());
			// mark the mower initial area as busy
			lawn.getArea(xy.getX(), xy.getY()).setBusy(true);
			// increment i for Mower name
			i++;
		}
		return new MowProcessor(lawn, mowers);
	}
	
	private static Queue<Command> createCommandQueue(String commands) {
		Queue<Command> res = new LinkedList<>();
		char[] letters = commands.toCharArray();
		for (char c : letters) {
			res.add(Command.valueOfCode(c));
		}
		return res;
	}
}
