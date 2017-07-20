package org.sagittarius.common.annotation;

import java.lang.reflect.Method;

import org.sagittarius.common.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugSuspendResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(DebugSuspendResolver.class);
	
	public static final String DEBUG_MSG = "-----DEBUG SUSPEND-----";

	public static void suspend(Method method) {
		if (method.isAnnotationPresent(DebugSuspend.class)) {
			logger.info(DEBUG_MSG);
			Delay.suspend();
		}
	}

}
