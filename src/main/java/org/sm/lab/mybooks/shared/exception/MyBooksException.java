package org.sm.lab.mybooks.shared.exception;

import net.customware.gwt.dispatch.shared.DispatchException;

import org.sm.lab.mybooks.shared.util.ErrorCode;

public class MyBooksException extends DispatchException {

	private static final long serialVersionUID = 1L;
	
	protected ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

	public MyBooksException() {
	}

	public MyBooksException(String message) {
		super(message);
	}

	public MyBooksException(Throwable cause) {
		super(cause);
	}

	public MyBooksException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MyBooksException(String message, ErrorCode code)
	{
		super(message);
		errorCode = code;
	}

	public MyBooksException(ErrorCode errorCode)
	{
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return this.errorCode;
	}

	public boolean hasMessage() {
		String message = getMessage();
		return message != null && !message.isEmpty();
	}

}
