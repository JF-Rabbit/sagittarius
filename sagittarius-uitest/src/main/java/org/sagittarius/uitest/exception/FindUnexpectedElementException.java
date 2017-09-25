package org.sagittarius.uitest.exception;

public class FindUnexpectedElementException extends RuntimeException {

	private static final long serialVersionUID = -8581105414200720051L;

	public FindUnexpectedElementException() {
		super();
	}

	public FindUnexpectedElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FindUnexpectedElementException(String message, Throwable cause) {
		super(message, cause);
	}

	public FindUnexpectedElementException(String message) {
		super(message);
	}

	public FindUnexpectedElementException(Throwable cause) {
		super(cause);
	}

}
