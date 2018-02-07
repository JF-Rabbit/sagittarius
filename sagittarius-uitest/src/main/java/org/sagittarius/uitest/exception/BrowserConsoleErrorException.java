package org.sagittarius.uitest.exception;

public class BrowserConsoleErrorException extends UIRuntimeException{

	private static final long serialVersionUID = 144854905082479372L;

	public BrowserConsoleErrorException() {
        super();
    }

    public BrowserConsoleErrorException(String message) {
        super(message);
    }

    public BrowserConsoleErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrowserConsoleErrorException(Throwable cause) {
        super(cause);
    }

    protected BrowserConsoleErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
