package com.cts.transpogov.exceptions;

public class CitizenNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CitizenNotFoundException(String message) {
		super(message);
	}

}
