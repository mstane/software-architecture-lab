package org.sm.lab.mybooks2.security;

public class PermissionNotDefinedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PermissionNotDefinedException() {
		super();
	}

	public PermissionNotDefinedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PermissionNotDefinedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionNotDefinedException(String message) {
		super(message);
	}

	public PermissionNotDefinedException(Throwable cause) {
		super(cause);
	}
	
	

}
