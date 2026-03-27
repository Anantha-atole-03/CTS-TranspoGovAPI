package com.cts.transpogov.exceptions;



public class AuditNotFoundException extends ComplianceNotFoundException {
	private static final long serialVersionUID = 1L;

    public AuditNotFoundException(String message) {
        super(message);
    }
}
