package org.sagittarius.uitest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sagittarius.uitest.driver.DriverManager;
import org.sagittarius.uitest.util.web.BrowserUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class BaseActionAop {

    private static final String ACTION_AOP_RULE = "execution(* org.sagittarius.uitest.util.BaseAction.*(..))";

    @Resource
    private DriverManager manager;

    @Around(ACTION_AOP_RULE)
    public Object actionAspect(ProceedingJoinPoint p) throws Throwable {
        Object obj;
        obj = p.proceed();
        boolean flag = true;
        if (flag) { // TODO flag need read file
            BrowserUtil.checkBrowserHaveErrorLog(manager);
        }
        return obj;
    }
}
