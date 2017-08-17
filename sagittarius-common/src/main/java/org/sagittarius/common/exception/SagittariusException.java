package org.sagittarius.common.exception;

public class SagittariusException extends Exception {

	private static final long serialVersionUID = 946581771552273587L;

	public SagittariusException() {
		super();
	}

	public SagittariusException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SagittariusException(String message, Throwable cause) {
		super(message, cause);
	}

	public SagittariusException(String message) {
		super(message);
	}

	public SagittariusException(Throwable cause) {
		super(cause);
	}

}
