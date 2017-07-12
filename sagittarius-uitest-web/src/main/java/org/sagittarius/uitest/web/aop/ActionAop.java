package org.sagittarius.uitest.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sagittarius.uitest.exception.ManualConfirmException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ActionAop {

	private static final String ACTION_AOP_RULE = "execution(public * org.sagittarius.uitest.web.action..*.*(..))";

	@Around(ACTION_AOP_RULE)
	public Object actionAspect(ProceedingJoinPoint p) throws Throwable {
		Object obj = null;
		try {
			obj = p.proceed();
			// TODO
			return obj;
		} catch (ManualConfirmException e) {
			// TODO set manual status
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

		return obj;
	}
}
