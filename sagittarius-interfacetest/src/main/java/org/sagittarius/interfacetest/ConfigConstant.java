package org.sagittarius.interfacetest;

public interface ConfigConstant {
	
	String CONFIG_FILE_PATH = ClassLoader.getSystemResource("config/config.yaml").getPath();
	String URL = "url";
	String PAS = "pas";
	
}
