package org.sagittarius.common;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class AopUtil {

    /**
     * 从ProceedingJoinPoint中获取当前调用的方法
     */
    public static Method getMethodFromProceedingJoinPoint(ProceedingJoinPoint p) throws NoSuchMethodException, SecurityException {
        Signature sig = p.getSignature();
        MethodSignature msig;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException();
        }
        msig = (MethodSignature) sig;
        Object target = p.getTarget();
        return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
    }
}
