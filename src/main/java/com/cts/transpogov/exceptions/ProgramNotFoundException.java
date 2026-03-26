package com.cts.transpogov.exceptions;


public class ProgramNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProgramNotFoundException(String msg) {
		super(msg);
	}

}
