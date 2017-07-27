package org.sagittarius.interfacetest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class DemoAop {
	
	private static final String ACTION_AOP_RULE = "execution(public * org.sagittarius.interfacetest.aop.Demo.execute())";
	
	@Around(ACTION_AOP_RULE)
	public Object actionAspect(ProceedingJoinPoint p) throws Throwable {
		System.out.println("******");
		Object obj = p.proceed();
		
		return obj;
	}

}
