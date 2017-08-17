package org.sagittarius.uitest.driver;

public interface DriverConstant {

	String SELENIUM_CONFIG_PATH = "classpath:config/selenium.properties";

	/* selenium properties */
	String DRIVER_TYPE = "${driver.type}";
	String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
	String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
	String WEBDRIVER_FIREFOX_BIN = "webdriver.firefox.bin";
	String REMOTE_HUB = "${remote.hub}";

	String WEBDRIVER_POINT_X = "${webdriver.point.x}";
	String WEBDRIVER_POINT_Y = "${webdriver.point.y}";
	String WEBDRIVER_Dimension_WIDTH = "${webdriver.dimension.width}";
	String WEBDRIVER_Dimension_HEIGHT = "${webdriver.dimension.height}";

	/* error msg */
	String LOAD_FILE_FAIL = "LOAD_FILE_FAIL";
	String DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES = "DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES";
	String DRIVER_TYPE_WRONG_ENUM = "DRIVER_TYPE_WRONG_ENUM";
	String WEBDRIVER_CHROME_DRIVER_NOT_FOUND = "WEBDRIVER_CHROME_DRIVER_NOT_FOUND";
	String WEBDRIVER_GECKO_DRIVER_NOT_FOUND = "WEBDRIVER_GECKO_DRIVER_NOT_FOUND";
	String WEBDRIVER_FIREFOX_BIN_NOT_FOUND = "WEBDRIVER_FIREFOX_BIN_NOT_FOUND";
	String DRIVER_IS_NULL = "DRIVER_IS_NULL";
	String REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES = "REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES";
	String REMOVTE_HUB_CONNECT_ERROR = "REMOVTE_HUB_CONNECT_ERROR";

}
