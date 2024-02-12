package com.fse4.skilltracker.event.exception;

public class SyncToReadDatabaseFailed extends RuntimeException {

	public SyncToReadDatabaseFailed(String message) {
		super(message);
	}
}
