package org.sagittarius.common.exception;

public class ExceptionUtil {

	public static String getErrorMsg(Throwable throwable) {
		if (throwable == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		if (throwable.getCause() != null) {
			builder.append("Cause: \n").append(throwable.getCause()).append("\n");
		}
		if (throwable.getMessage() != null) {
			builder.append("Message: \n\t").append(throwable.getMessage()).append("\n");
		}
		if (throwable.getStackTrace() != null && throwable.getStackTrace().length != 0) {
			builder.append("StackTrace: \n");
			for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
				builder.append("\t").append(stackTraceElement.toString()).append("\n");
			}
		}
		return builder.toString();
	}
}
