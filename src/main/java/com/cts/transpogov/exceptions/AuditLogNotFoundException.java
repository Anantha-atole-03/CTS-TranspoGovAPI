package com.cts.transpogov.exceptions;

public class AuditLogNotFoundException extends RuntimeException {
	public AuditLogNotFoundException(String sms) {
		super(sms);
	}

}
