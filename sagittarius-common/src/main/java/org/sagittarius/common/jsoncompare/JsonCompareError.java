package org.sagittarius.common.jsoncompare;

public class JsonCompareError extends Error {

	private static final long serialVersionUID = 1L;

	public JsonCompareError() {
		super();
	}

	public JsonCompareError(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public JsonCompareError(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public JsonCompareError(String arg0) {
		super(arg0);
	}

	public JsonCompareError(Throwable arg0) {
		super(arg0);
	}

}
