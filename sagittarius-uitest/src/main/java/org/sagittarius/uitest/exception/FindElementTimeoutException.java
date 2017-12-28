package org.sagittarius.uitest.exception;

/**
 * FindElementTimeoutException超时异常
 * 
 * @author jasonzhang 2016年11月3日 下午5:43:29
 *
 */
public class FindElementTimeoutException extends UIRuntimeException {

	private static final long serialVersionUID = 1L;

	public FindElementTimeoutException() {
		super();
	}

	public FindElementTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FindElementTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public FindElementTimeoutException(String message) {
		super(message);
	}

	public FindElementTimeoutException(Throwable cause) {
		super(cause);
	}

}
