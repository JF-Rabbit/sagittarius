package org.sagittarius.uitest.driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.sagittarius.common.properties.PropertiesUtil;
import org.sagittarius.uitest.exception.DriverInitException;
import org.springframework.stereotype.Component;

@Component
public class DriverManager {

	private static final String SELENIUM_PROPERTIES = "selenium.properties";
	private static final String SELENIUM_PROPERTIES_PATH = "conf/selenium.properties";

	private static final String CHROME = "chrome";

	private static final String LOAD_FILE_FAIL = "LOAD_FILE_FAIL";
	private static final String DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES = "DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES";
	private static final String DRIVER_TYPE_WRONG_ENUM = "DRIVER_TYPE_WRONG_ENUM";
	private static final String DRIVER_KEY_OR_PATH_NOT_FOUND_IN_PROPERTIES = "DRIVER_KEY_OR_PATH_NOT_FOUND_IN_PROPERTIES";
	private static final String DRIVER_IS_NULL = "DRIVER_IS_NULL";
	private static final String REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES = "REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES";
	private static final String REMOVTE_HUB_CONNECT_ERROR = "REMOVTE_HUB_CONNECT_ERROR";

	private static final String DRIVER_TYPE = "driver.type";
	private static final String DRIVER_KEY = "driver.key";
	private static final String DRIVER_LOCATION = "driver.path";
	private static final String REMOTE_HUB = "remote.hub";
	
	public static final int DEFAULT_FIND_ELEMENT_TIMEOUT = 3;

	public enum DriverType {
		LOCAL_CHROME, REMOTE_CHROME
	}

	private Map<String, Object> loadDriverTypeFromSource() throws DriverInitException {
		Properties properties = new Properties();
		try {
			properties = PropertiesUtil.load(SELENIUM_PROPERTIES_PATH);
		} catch (IOException e) {
			throw new DriverInitException(LOAD_FILE_FAIL, e);
		}

		String driverTypeStr = properties.getProperty(DRIVER_TYPE);
		if (driverTypeStr == null) {
			throw new DriverInitException(DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES);
		}

		DriverType driverType = null;
		try {
			driverType = DriverType.valueOf(driverTypeStr);
		} catch (IllegalArgumentException e) {
			throw new DriverInitException(DRIVER_TYPE_WRONG_ENUM, e);
		}

		Map<String, Object> map = new HashMap<>();
		map.put(SELENIUM_PROPERTIES, properties);
		map.put(DRIVER_TYPE, driverType);

		return map;
	}

	private void setLocalDriverKey$PathBySource(Properties properties, String driverType) throws DriverInitException {
		String webDriverKey = properties.getProperty(driverType + "." + DRIVER_KEY);
		String webDriverPath = properties.getProperty(driverType + "." + DRIVER_LOCATION);

		if (webDriverKey == null || webDriverPath == null) {
			String errorMsg = driverType + "." + DRIVER_KEY + " | " + driverType + "." + DRIVER_LOCATION;
			throw new DriverInitException(DRIVER_KEY_OR_PATH_NOT_FOUND_IN_PROPERTIES + ":" + errorMsg);
		}
		System.setProperty(webDriverKey, webDriverPath);
	}

	private String getRemoteHubAddressFromSource(Properties properties) throws DriverInitException {
		String remoteHub = properties.getProperty(REMOTE_HUB);
		if (remoteHub == null) {
			throw new DriverInitException(REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES);
		}
		return remoteHub;
	}

	private RemoteWebDriver setRemoteWebDriver(DesiredCapabilities capabilities, Properties properties)
			throws DriverInitException {
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability("name", "Remote File Upload using Selenium 2's FileDetectors");
		RemoteWebDriver remoteWebDriver = null;
		try {
			remoteWebDriver = new RemoteWebDriver(new URL(getRemoteHubAddressFromSource(properties)), capabilities);
		} catch (MalformedURLException e) {
			throw new DriverInitException(REMOVTE_HUB_CONNECT_ERROR, e);
		}
		remoteWebDriver.setFileDetector(new LocalFileDetector());
		return remoteWebDriver;
	}

	public WebDriver getDriver() throws DriverInitException {
		Map<String, Object> droverConfigMap = this.loadDriverTypeFromSource();
		DriverType driverType = (DriverType) droverConfigMap.get(DRIVER_TYPE);
		Properties properties = (Properties) droverConfigMap.get(SELENIUM_PROPERTIES);

		WebDriver driver = null;
		DesiredCapabilities capabilities = null;
		switch (driverType) {
		case LOCAL_CHROME:
			this.setLocalDriverKey$PathBySource(properties, CHROME);
			driver = new ChromeDriver();
			break;
		case REMOTE_CHROME:
			capabilities = DesiredCapabilities.chrome();
			driver = setRemoteWebDriver(capabilities, properties);
			break;
		default:
			break;
		}

		if (driver == null) {
			throw new DriverInitException(DRIVER_IS_NULL);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(DEFAULT_FIND_ELEMENT_TIMEOUT, TimeUnit.SECONDS);

		return driver;
	}

	public void quitDriver(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}
}
