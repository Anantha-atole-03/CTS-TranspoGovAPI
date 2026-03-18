package com.cts.transpogov.exceptions;


public class InvalidUsernamePasswordException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public InvalidUsernamePasswordException(String msg) {
		super(msg);
	}

}
