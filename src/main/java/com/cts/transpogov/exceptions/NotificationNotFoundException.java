package com.cts.transpogov.exceptions;

public class NotificationNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotificationNotFoundException(String msg) {
		super(msg);
	}
}
