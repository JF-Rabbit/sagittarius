package org.sagittarius.uitest.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.sagittarius.common.properties.PropertiesUtil;
import org.sagittarius.uitest.exception.DriverInitException;
import org.sagittarius.uitest.util.web.Browser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DriverManager {

	private static final String SELENIUM_PROPERTIES = "selenium.properties";
	private static final String SELENIUM_PROPERTIES_PATH = "conf/selenium.properties";

	private static final String LOAD_FILE_FAIL = "LOAD_FILE_FAIL";
	private static final String DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES = "DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES";
	private static final String DRIVER_TYPE_WRONG_ENUM = "DRIVER_TYPE_WRONG_ENUM";
	private static final String WEBDRIVER_CHROME_DRIVER_NOT_FOUND = "WEBDRIVER_CHROME_DRIVER_NOT_FOUND";
	private static final String WEBDRIVER_GECKO_DRIVER_NOT_FOUND = "WEBDRIVER_GECKO_DRIVER_NOT_FOUND";
	private static final String WEBDRIVER_FIREFOX_BIN_NOT_FOUND = "WEBDRIVER_FIREFOX_BIN_NOT_FOUND";
	private static final String DRIVER_IS_NULL = "DRIVER_IS_NULL";
	private static final String REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES = "REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES";
	private static final String REMOVTE_HUB_CONNECT_ERROR = "REMOVTE_HUB_CONNECT_ERROR";

	private static final String DRIVER_TYPE = "driver.type";
	private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
	private static final String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
	private static final String WEBDRIVER_FIREFOX_BIN = "webdriver.firefox.bin";
	private static final String REMOTE_HUB = "remote.hub";

	public static final int DEFAULT_FIND_ELEMENT_TIMEOUT = 3;

	private Browser browser;

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	public enum DriverType {
		LOCAL_CHROME, REMOTE_CHROME, LOCAL_FIREFOX,
	}

	private Map<String, Object> loadDriverTypeFromSource() throws DriverInitException {
		Properties properties = new Properties();
		try {
			properties = PropertiesUtil.load(SELENIUM_PROPERTIES_PATH);
		} catch (Exception e) {
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

	private void setChromeConfig(Properties properties) throws DriverInitException {
		String webdriver_chrome_driver = properties.getProperty(WEBDRIVER_CHROME_DRIVER);

		if (webdriver_chrome_driver == null) {
			String errorMsg = WEBDRIVER_CHROME_DRIVER + " | " + webdriver_chrome_driver;
			throw new DriverInitException(WEBDRIVER_CHROME_DRIVER_NOT_FOUND + ":" + errorMsg);
		}
		System.setProperty(WEBDRIVER_CHROME_DRIVER, webdriver_chrome_driver);
	}

	private void setFirefoxConfig(Properties properties) throws DriverInitException {
		String webdriver_gecko_driver = properties.getProperty(WEBDRIVER_GECKO_DRIVER);
		String webdriver_firefox_bin = properties.getProperty(WEBDRIVER_FIREFOX_BIN);

		if (webdriver_gecko_driver == null) {
			String errorMsg = WEBDRIVER_GECKO_DRIVER + " | " + webdriver_gecko_driver;
			throw new DriverInitException(WEBDRIVER_GECKO_DRIVER_NOT_FOUND + ":" + errorMsg);
		}
		if (webdriver_firefox_bin == null) {
			String errorMsg = WEBDRIVER_FIREFOX_BIN + " | " + webdriver_firefox_bin;
			throw new DriverInitException(WEBDRIVER_FIREFOX_BIN_NOT_FOUND + ":" + errorMsg);
		}
		System.setProperty(WEBDRIVER_GECKO_DRIVER, webdriver_gecko_driver);
		System.setProperty(WEBDRIVER_FIREFOX_BIN, webdriver_firefox_bin);
	}

	private String getRemoteHubAddressFromSource(Properties properties) throws DriverInitException {
		String remoteHub = properties.getProperty(REMOTE_HUB);
		if (remoteHub == null) {
			throw new DriverInitException(REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES);
		}
		return remoteHub;
	}

	private RemoteWebDriver setRemoteWebDriver(DesiredCapabilities capabilities, Properties properties) throws DriverInitException {
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
			this.setChromeConfig(properties);
			driver = new ChromeDriver();
			driver = setBrowserLayout(driver, properties);

			setBrowser(Browser.CHROME);
			break;
		case REMOTE_CHROME:
			capabilities = DesiredCapabilities.chrome();
			driver = setRemoteWebDriver(capabilities, properties);
			break;
		case LOCAL_FIREFOX:
			this.setFirefoxConfig(properties);
			// skip SSL
			// ProfilesIni profilesIni = new ProfilesIni();
			// FirefoxProfile profile = profilesIni.getProfile("default");
			// profile.setAcceptUntrustedCertificates(true);
			// profile.setAssumeUntrustedCertificateIssuer(true);

			// FirefoxOptions options = new FirefoxOptions();
			// options.setProfile(profile);

			capabilities = DesiredCapabilities.firefox();

			driver = new FirefoxDriver(capabilities);
			/*
			 * XXX can't set maximize for firefox
			 * 
			 * use maximize history manually to skip
			 *
			 * github bug: https://github.com/mozilla/geckodriver/issues/820
			 * 
			 */
			driver = setBrowserLayout(driver, properties);
			setBrowser(Browser.FIREFOX);
			break;
		}

		if (driver == null) {
			throw new DriverInitException(DRIVER_IS_NULL);
		}

		driver.manage().timeouts().implicitlyWait(DEFAULT_FIND_ELEMENT_TIMEOUT, TimeUnit.SECONDS);

		return driver;
	}

	private static final String WEBDRIVER_POINT_X = "webdriver.point.x";
	private static final String WEBDRIVER_POINT_Y = "webdriver.point.y";
	private static final String WEBDRIVER_Dimension_WIDTH = "webdriver.dimension.width";
	private static final String WEBDRIVER_Dimension_HEIGHT = "webdriver.dimension.height";

	private WebDriver setBrowserLayout(WebDriver driver, Properties properties) throws DriverInitException {

		WebDriver.Window window = driver.manage().window();
		if (properties.get(WEBDRIVER_POINT_X) != null && properties.get(WEBDRIVER_POINT_Y) != null) {
			window.setPosition(
					new Point(PropertiesUtil.getInt(properties, WEBDRIVER_POINT_X), PropertiesUtil.getInt(properties, WEBDRIVER_POINT_Y)));
		}

		if (properties.get(WEBDRIVER_Dimension_WIDTH) != null && properties.get(WEBDRIVER_Dimension_HEIGHT) != null) {
			window.setSize(new Dimension(PropertiesUtil.getInt(properties, WEBDRIVER_Dimension_WIDTH),
					PropertiesUtil.getInt(properties, WEBDRIVER_Dimension_HEIGHT)));
		} else {
			if (browser != Browser.CHROME) {
				throw new DriverInitException(
						"can't set maximize for firefox, github bug: https://github.com/mozilla/geckodriver/issues/820");
			}
			window.maximize();
		}

		return driver;
	}

	public void quitDriver(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}
}
