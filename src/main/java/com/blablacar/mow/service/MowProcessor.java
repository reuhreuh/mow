package com.blablacar.mow.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blablacar.mow.model.Area;
import com.blablacar.mow.model.Command;
import com.blablacar.mow.model.Coordinates;
import com.blablacar.mow.model.Lawn;
import com.blablacar.mow.model.Mower;
import com.blablacar.mow.model.Orientation;
import com.blablacar.mow.model.Position;

/**
 * Once constructed, the {@link MowProcessor} expose the processing method {@link MowProcessor#mow()}.
 * It performs the mowing job by launching asynchronous thread for each mower. The thread pool is sized with
 * 10 thread by default.<br />
 * Once finished, the position of each mower is printed out.
 * 
 * @author laurent
 *
 */
public class MowProcessor {

	private static Logger logger = LoggerFactory.getLogger(MowProcessor.class);
	
	private Lawn lawn;
	private List<Mower> mowers;

	/**
	 * Init the process with given lawn and configured mowers
	 * @param lawn the lawn
	 * @param mowers the configured mowers
	 */
	public MowProcessor(Lawn lawn, List<Mower> mowers) {
		this.lawn = lawn;
		this.mowers = mowers;
	}

	/**
	 * Perform the mowing job, and display mowers position when job is done.
	 */
	public void mow() {
		final Long startTime = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (Mower m : mowers) {
		    executorService.submit(new Runnable() {
		        @Override
		        public void run() {
		          mow(m);
		        }
		      });
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(30, TimeUnit.MINUTES);
			final Long duration = (System.currentTimeMillis() - startTime);
			logger.warn("Mowing time: {}ms", duration);
			//System.out.println("Mowing time:" + duration + "ms");
			printResult();
		} catch (InterruptedException e) {
			logger.error("Error while awaiting all the processing cowers", e);
		}
	}
	
	private void printResult(){
		for (Mower mower : mowers) {
			System.out.println(mower.getPosition());
		}
	}

	private void mow(Mower m) {
		while(!m.getCommands().isEmpty()){
			Command c = m.getCommands().poll();
			logger.info("Processing command '{}' for '{}' ({})", c, m.getName(), m.getPosition());
			Position newPosition = computeNext(m.getPosition(), c);
			if(Command.LEFT == c || Command.RIGHT == c) {
				// rotation command, the area is already busy by the mower itself, we're safe
				m.setPosition(newPosition);
				logger.info("'{}' new position: {}", m.getName(), newPosition);
			} else {
				// forward command, can be discarded by an unauthorized move (out of lawn bound or busy area)
				// thus, Area status update has to be synchronized 
				safeForward(m, newPosition);				
			}
		}
	}
	
	/**
	 * Thread safe block, checking and updating {@code Area 'isBusy'} flag.
	 * @param m the mower 
	 * @param newPosition the new position
	 */
	private synchronized void safeForward(Mower m, Position newPosition) {
		if(isAllowed(newPosition)){
			Area oldArea = lawn.getArea(m.getPosition().getCoordinates().getX(), m.getPosition().getCoordinates().getY());
			oldArea.setBusy(false);
			m.setPosition(newPosition);
			logger.info("'{}' new position: {}", m.getName(), newPosition);
			Area area = lawn.getArea(newPosition.getCoordinates().getX(), newPosition.getCoordinates().getY());
			area.setBusy(true);
		} else {
			logger.warn("Discarded forward for '{}' from ({}) to ({})",  m.getName(), m.getPosition(), newPosition);
		}
	}

	
	/**
	 * Check if the provided position is available. Return <code>false</code> 
	 * <ul>
	 * <li>The new position is out of the lawn grid</li>
	 * <li>A mower is already on the new position</li>
	 * </ul>
	 * @param newPosition the candidate new position
	 * @return <code>true</code> if the new position is available, <code>false</code> if not.
	 */
	private synchronized boolean isAllowed(Position newPosition) {
		// check if new position exists on the lawn
		Area a = null;
		try {
			a = lawn.getArea(newPosition.getCoordinates().getX(), newPosition.getCoordinates().getY());
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.warn("Area ({},{}) is out of Lawn boundaries", newPosition.getCoordinates().getX(), newPosition.getCoordinates().getY());
			return false;
		}
		if(a.isBusy()) {
			logger.warn("Area ({},{}) is busy by another mower", a.getCoordinates().getX(), a.getCoordinates().getY());
			return false;
		}
		return true;
	}

	private Position computeNext(Position current, Command c){
		Coordinates newXY;
		Orientation newOrientation;
		switch (c) {
		case LEFT:
		case RIGHT:
			// we remain on same Area
			newXY = current.getCoordinates();
			// we rotate
			newOrientation = rotate(current.getOrientation(), c);
			break;
		case FORWARD:
			// compute the next area XY
			newXY = computeForwardMove(current);
			// we keep same orientation
			newOrientation = current.getOrientation();
			break;
		default:
			throw new IllegalStateException("This command is not implemented");
		}
		return new Position(newXY, newOrientation);
	}

	private Orientation rotate(Orientation orientation, Command c) {
		// get current Enum value position order (i.e: declaration order matters!)
		int i = orientation.ordinal();
		if(Command.LEFT == c){
			i--;
		} else if(Command.RIGHT == c){
			i++;
		} else {
			// shouldnt come here
			throw new IllegalStateException("Unexpected rotation command: " + c);
		}
		// limit values
		if(i > 3) {
			i = 0;
		} else if (i < 0){
			i = 3;
		}
		Orientation res = Orientation.values()[i];
		logger.debug("rotate(): o={},c={} => o={}", orientation, c, res);
		return res;
	}

	private Coordinates computeForwardMove(Position current) {
		int xOffset = 0;
		int yOffset = 0;
		switch(current.getOrientation()){
		case NORTH:
			yOffset++;
			break;
		case SOUTH:
			yOffset--;
			break;
		case EAST:
			xOffset++;
			break;
		case WEST:
			xOffset--;
			break;
		default:
			throw new IllegalStateException("This orientation is not implemented");
		}
		Coordinates res = new Coordinates(current.getCoordinates().getX() + xOffset, current.getCoordinates().getY() + yOffset);
		logger.debug("computeForwardMove(): position={} => coordinates=({},{})", current, res.getX(), res.getY());
		return res;
	}

	public Lawn getLawn() {
		return lawn;
	}

	public List<Mower> getMowers() {
		return mowers;
	}
}
