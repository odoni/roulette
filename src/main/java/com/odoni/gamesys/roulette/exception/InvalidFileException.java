package com.odoni.gamesys.roulette.exception;

public class InvalidFileException extends Exception {

	public InvalidFileException(String message) {
		super(message);
	}

	public InvalidFileException(String message, Throwable cause) {
		super(message, cause);
	}
}
