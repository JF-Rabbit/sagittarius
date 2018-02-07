package org.sagittarius.uitest.exception;

public class UIException extends Exception {

	private static final long serialVersionUID = 6822952807310450861L;

	public UIException() {
        super();
    }

    public UIException(String message) {
        super(message);
    }

    public UIException(String message, Throwable cause) {
        super(message, cause);
    }

    public UIException(Throwable cause) {
        super(cause);
    }

    protected UIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
