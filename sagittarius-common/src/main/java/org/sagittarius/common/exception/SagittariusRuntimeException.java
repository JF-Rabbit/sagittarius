package org.sagittarius.common.exception;

public class SagittariusRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SagittariusRuntimeException() {
		super();
	}

	public SagittariusRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SagittariusRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SagittariusRuntimeException(String message) {
		super(message);
	}

	public SagittariusRuntimeException(Throwable cause) {
		super(cause);
	}

}
