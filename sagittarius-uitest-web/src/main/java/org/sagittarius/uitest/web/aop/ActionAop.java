package org.sagittarius.uitest.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sagittarius.common.Delay;
import org.sagittarius.common.annotation.DebugSuspend;
import org.sagittarius.common.aop.AopUtil;
import org.sagittarius.common.date.DateUtil;
import org.sagittarius.common.debug.ScannerDebug;
import org.sagittarius.common.judge.JudgeUtil;
import org.sagittarius.uitest.CommonConstant;
import org.sagittarius.uitest.driver.DriverManager;
import org.sagittarius.uitest.exception.ManualConfirmException;
import org.sagittarius.uitest.util.web.WebElementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
@PropertySource({AOPConstant.AOP_CONFIG_FILE_PATH})
public class ActionAop extends ScannerDebug implements AOPConstant, CommonConstant {

    private static final Logger logger = LoggerFactory.getLogger(ActionAop.class);

    @Value(RUN_TYPE)
    private String runType;

    @Value(DEBUG_TYPE_ERROR)
    private String debugError;

    @Value(DEBUG_TYPE_ANNOTATION)
    private String debugAnnotation;

    @Value(DEBUG_TYPE_EVERY_ACTION)
    private String debugEveryAction;

    @Value(DEBUG_SAVE_WEB_HTML)
    private String debugSaveWebHtml;

    @Value(LOG_PATH)
    private String logPath;

    private static final String ACTION_AOP_RULE = "execution(public * org.sagittarius.uitest.web.action..*.*(..))";

    private static final String ACTION_START = "==> ";
    private static final String ACTION_END = "<== ";

    private static final String DEBUG_MSG = "=====DEBUG SUSPEND=====\n\tEnter C or S to continue/stop test";

    @Around(ACTION_AOP_RULE)
    public Object actionAspect(ProceedingJoinPoint p) throws Throwable {
        Object obj = null;
        Method method = AopUtil.getMethodFromProceedingJoinPoint(p);
        try {
            logger.info(ACTION_START + method.getName());
            obj = p.proceed();
            logger.info(ACTION_END + method.getName());
            // TODO handle result
            everyActionDebug();
            suspendAnnotation(method);

            return obj;
        } catch (ManualConfirmException e) {
            // TODO set manual status
        } catch (Exception e) {
            // TODO: handle exception
            saveWebPage();
            errorDebug(e);
            throw e;
        }

        return obj;
    }

    private void saveWebPage() {
        if (JudgeUtil.isTrue(debugSaveWebHtml)) {
            String fileName = logPath + DateUtil.dateFormat(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_FILE_TYPE)
                    + FILE_TYPE_HTML;
            WebElementUtil.saveCurrentWebPage(fileName);
            Delay.sleep(5000);
        }
    }

    private void errorDebug(Exception e) {
        if (runType.equals(RUN_TYPE_NORMAL)) {
            return;
        } else if (runType.equals(RUN_TYPE_DEBUG) && JudgeUtil.isTrue(debugError)) {
            logger.error(DEBUG_MSG, e);
            debugWaiting();
        }

    }

    private void everyActionDebug() {
        if (runType.equals(RUN_TYPE_NORMAL)) {
            return;
        } else if (runType.equals(RUN_TYPE_DEBUG) && JudgeUtil.isTrue(debugEveryAction)) {
            logger.info(DEBUG_MSG);
            debugWaiting();
        }
    }

    private void suspendAnnotation(Method method) {
        if (runType.equals(RUN_TYPE_NORMAL)) {
            return;
        } else if (runType.equals(RUN_TYPE_DEBUG) && JudgeUtil.isTrue(debugAnnotation)) {

            if (method.isAnnotationPresent(DebugSuspend.class)) {
                logger.info(DEBUG_MSG);
                debugWaiting();
            }
        }
    }

    @Resource
    private DriverManager manager;

    @Override
    public void beforeExit() {
        logger.info("\nStop test");
    }


}
