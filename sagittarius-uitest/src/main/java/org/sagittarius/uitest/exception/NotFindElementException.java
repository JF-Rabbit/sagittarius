package org.sagittarius.uitest.exception;

public class NotFindElementException extends RuntimeException {

	private static final long serialVersionUID = -8581105414200720051L;

	public NotFindElementException() {
		super();
	}

	public NotFindElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotFindElementException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFindElementException(String message) {
		super(message);
	}

	public NotFindElementException(Throwable cause) {
		super(cause);
	}
}
