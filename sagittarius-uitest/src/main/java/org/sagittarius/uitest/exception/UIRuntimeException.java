package org.sagittarius.uitest.exception;

public class UIRuntimeException extends RuntimeException {

    public UIRuntimeException() {
        super();
    }

    public UIRuntimeException(String message) {
        super(message);
    }

    public UIRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UIRuntimeException(Throwable cause) {
        super(cause);
    }

    protected UIRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
