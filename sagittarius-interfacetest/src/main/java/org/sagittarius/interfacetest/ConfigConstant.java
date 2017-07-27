package org.sagittarius.interfacetest;

import org.sagittarius.common.properties.PropertiesUtil;

public interface ConfigConstant {
	
	String CONFIG_FILE_PATH = "conf/config.properties";

	String ADDRESS_TYPE = "address.type";
	
	String MODUAL_PAS = PropertiesUtil.getSingleValue(CONFIG_FILE_PATH, ADDRESS_TYPE) + "pas";
}
