package org.sagittarius.uitest.exception;

public class DriverInitException extends UIException {

	private static final long serialVersionUID = 1L;

	public DriverInitException() {
		super();
	}

	public DriverInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DriverInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public DriverInitException(String message) {
		super(message);
	}

	public DriverInitException(Throwable cause) {
		super(cause);
	}

}
