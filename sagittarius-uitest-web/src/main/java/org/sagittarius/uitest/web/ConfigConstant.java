package org.sagittarius.uitest.web;

public interface ConfigConstant {

	String ENV_CONFIG_PATH = ClassLoader.getSystemResource("config/env.yaml").getPath();
	
	String ENV = "env";
	String URL = "url";
	String USERNAME = "username";
	String PASSWORD = "password";
	
}
