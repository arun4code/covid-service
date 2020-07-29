package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CovidReportException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CovidReportException() {
		super("User does not exist");
	}

	public CovidReportException(String message) {
		super(message);
	}

}
