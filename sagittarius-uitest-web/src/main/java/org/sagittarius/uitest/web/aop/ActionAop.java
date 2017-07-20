package org.sagittarius.uitest.web.aop;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sagittarius.common.Delay;
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
public class ActionAop implements ConfigConstant, CommonConstant{
	
	private static final Logger logger = LoggerFactory.getLogger(ActionAop.class);

	private static final String ACTION_AOP_RULE = "execution(public * org.sagittarius.uitest.web.action..*.*(..))";
	
	private String logPath;
	
	ActionAop() throws IOException{
		this.logPath = getLogPath();
	}
	
	private String getLogPath() throws IOException {
		Properties properties = PropertiesUtil.load(CONFIG_FILE_PATH);
		return properties.getProperty(LOG_PATH);
	}
	
	private String getRunType() throws IOException {
		Properties properties = PropertiesUtil.load(CONFIG_FILE_PATH);
		return properties.getProperty(RUN_TYPE);
	}

	@Around(ACTION_AOP_RULE)
	public Object actionAspect(ProceedingJoinPoint p) throws Throwable {
		Object obj = null;
		try {
			obj = p.proceed();
			// TODO handle result
			return obj;
		} catch (ManualConfirmException e) {
			// TODO set manual status
		} catch (Exception e) {
			// TODO: handle exception
			String fileName = logPath + DateUtil.dateFormat(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_FILE_TYPE) + FILE_TYPE_HTML;
			WebElementUtil.saveCurrentWebPage(fileName);
			Delay.sleep(5000);
			
			if (getRunType().equals(RUN_TYPE_DEBUG)){
				logger.error("-----DEBUG SUSPEND-----", e);
				Delay.suspend();
			}
			throw e;
		}

		return obj;
	}
}
