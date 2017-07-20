package org.sagittarius.uitest.web.aop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sagittarius.common.Delay;
import org.sagittarius.common.annotation.DebugSuspendResolver;
import org.sagittarius.common.aop.AopUtil;
import org.sagittarius.common.date.DateUtil;
import org.sagittarius.common.properties.PropertiesUtil;
import org.sagittarius.uitest.CommonConstant;
import org.sagittarius.uitest.exception.ManualConfirmException;
import org.sagittarius.uitest.util.web.WebElementUtil;
import org.sagittarius.uitest.web.ConfigConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ActionAop implements ConfigConstant, CommonConstant {

	private static final Logger logger = LoggerFactory.getLogger(ActionAop.class);

	private static final String ACTION_AOP_RULE = "execution(public * org.sagittarius.uitest.web.action..*.*(..))";
	
	private static final String ACTION_START = "ACTION_START ";
	private static final String ACTION_END = "ACTION_END ";
	private static final String FOO_L = "---------- ";
	private static final String FOO_R = " ----------";

	private Properties properties;

	ActionAop() throws IOException {
		this.properties = PropertiesUtil.load(CONFIG_FILE_PATH);
	}

	@Around(ACTION_AOP_RULE)
	public Object actionAspect(ProceedingJoinPoint p) throws Throwable {
		Object obj = null;
		Method method = AopUtil.getMethodFromProceedingJoinPoint(p);
		try {
			logger.info(FOO_L + ACTION_START + method.getName() + FOO_R);
			obj = p.proceed();
			logger.info(FOO_L + ACTION_END + method.getName() + FOO_R);
			// TODO handle result
			everyActionDebug();
			
			DebugSuspendResolver.suspend(method);
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
		String fileName = properties.getProperty(LOG_PATH) + DateUtil.dateFormat(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_FILE_TYPE) + FILE_TYPE_HTML;
		WebElementUtil.saveCurrentWebPage(fileName);
		Delay.sleep(5000);
	}

	private void errorDebug(Exception e) {
		if(properties.get(RUN_TYPE).equals(RUN_TYPE_NORMAL)){
			return;
		}else if (properties.get(RUN_TYPE).equals(RUN_TYPE_DEBUG) && properties.get(DEBUG_TYPE_ERROR_DEBUG).equals(TRUE)) {
			logger.error(DebugSuspendResolver.DEBUG_MSG, e);
			Delay.suspend();
		}
	}

	private void everyActionDebug() {
		if(properties.get(RUN_TYPE).equals(RUN_TYPE_NORMAL)){
			return;
		}else if (properties.get(RUN_TYPE).equals(RUN_TYPE_DEBUG) && properties.get(DEBUG_TYPE_EVERY_ACTION_DEBUG).equals(TRUE)) {
			logger.info(DebugSuspendResolver.DEBUG_MSG);
			Delay.suspend();
		}
	}
}
