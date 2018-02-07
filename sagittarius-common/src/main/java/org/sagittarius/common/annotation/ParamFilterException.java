package org.sagittarius.common.annotation;

public class ParamFilterException extends Exception {

	private static final long serialVersionUID = -826573994586794324L;

	public ParamFilterException() {
        super();
    }

    public ParamFilterException(String message) {
        super(message);
    }

    public ParamFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamFilterException(Throwable cause) {
        super(cause);
    }

    protected ParamFilterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
