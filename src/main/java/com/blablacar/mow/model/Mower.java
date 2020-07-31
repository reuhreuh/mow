package com.blablacar.mow.model;

import java.util.Queue;

/**
 * POJO representing a mower, with its name, its position (x, y and orientation) and the list of commands.
 * @author laurent
 *
 */
public class Mower {

	private String name;
	private Position position;
	private Queue<Command> commands;
	
	public Mower(final String name, Position position, final Queue<Command> commands){
		this.setName(name);
		this.position = position;
		this.setCommands(commands);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Queue<Command> getCommands() {
		return commands;
	}

	public void setCommands(final Queue<Command> commands) {
		this.commands = commands;
	}

	@Override
	public String toString() {
		return String.format("'%s' with position [%s] and commands %s", this.name, position.toString(), commands.toString());
	}
}
