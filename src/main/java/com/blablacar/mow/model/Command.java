package com.blablacar.mow.model;

/**
 * Represents the possible moving command for a mower.
 * @author laurent
 *
 */
public enum Command {
	
	LEFT('L'),
	RIGHT('R'),
	FORWARD('F');
	
	private char code;
	
	private Command(final char code){
		this.code = code;
	}
	
	public char getCode(){
		return this.code;
	}
	
	/**
	 * Retrieve a Command value from the character code.
	 * @param code the character code
	 * @return a Command
	 * @throws IllegalArgumentException if no value can be found for given code.
	 */
	public static Command valueOfCode(final char code) {
		switch (code) {
		case 'L':
			return LEFT;
		case 'R':
			return RIGHT;
		case 'F':
			return FORWARD;
		default:
			throw new IllegalArgumentException("Unknown command code: " + code);
		}
	}
}
