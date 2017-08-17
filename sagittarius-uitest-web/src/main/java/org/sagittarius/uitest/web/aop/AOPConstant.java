package org.sagittarius.uitest.web.aop;

public interface AOPConstant {

	String AOP_CONFIG_FILE_PATH = "classpath:config/aop.properties";

	String RUN_TYPE = "${run.type}";
	String RUN_TYPE_NORMAL = "normal";
	String RUN_TYPE_DEBUG = "debug";

	String DEBUG_TYPE_ERROR = "${debug.error}";
	String DEBUG_TYPE_ANNOTATION = "${debug.annotation}";
	String DEBUG_TYPE_EVERY_ACTION = "${debug.every.action}";
	String DEBUG_SAVE_WEB_HTML = "${debug.save.web.html}";

	String LOG_PATH = "${log.path}";
}
