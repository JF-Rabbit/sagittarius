package org.sagittarius.uitest.exception;

public class ManualConfirmException extends Exception {

	private static final long serialVersionUID = 1L;

	public ManualConfirmException() {
		super();
	}

	public ManualConfirmException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ManualConfirmException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ManualConfirmException(String arg0) {
		super(arg0);
	}

	public ManualConfirmException(Throwable arg0) {
		super(arg0);
	}

}
