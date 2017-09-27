package org.sagittarius.uitest.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.sagittarius.uitest.exception.DriverInitException;
import org.sagittarius.uitest.util.web.Browser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@PropertySource({ DriverConstant.SELENIUM_CONFIG_PATH })
public class DriverManager implements DriverConstant {

	@Value(DRIVER_TYPE)
	private String driverType;

	@Value("${" + WEBDRIVER_CHROME_DRIVER + "}")
	private String chromeDriverPath;

	@Value("${" + WEBDRIVER_GECKO_DRIVER + "}")
	private String geckoDriverPath;

	@Value("${" + WEBDRIVER_FIREFOX_BIN + "}")
	private String firefoxDriverPath;

	@Value(REMOTE_HUB)
	private String remoteHub;

	@Value(WEBDRIVER_POINT_X)
	private String browserX;

	@Value(WEBDRIVER_POINT_Y)
	private String browserY;

	@Value(WEBDRIVER_Dimension_WIDTH)
	private String browserWidth;

	@Value(WEBDRIVER_Dimension_HEIGHT)
	private String browserHeigh;

	public static final int DEFAULT_FIND_ELEMENT_TIMEOUT = 3;
	public static final int DEFAULT_PAGE_LOAD_TIMEOUT = 10;

	private Browser browser;

	public Browser getBrowser() {
		return browser;
	}

	public enum DriverType {
		LOCAL_CHROME, REMOTE_CHROME, LOCAL_FIREFOX,
	}

	private DriverType loadDriverTypeFromSource() throws DriverInitException {

		if (StringUtils.isEmpty(driverType)) {
			throw new DriverInitException(DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES);
		}

		DriverType type = null;
		try {
			type = DriverType.valueOf(driverType);
		} catch (IllegalArgumentException e) {
			throw new DriverInitException(DRIVER_TYPE_WRONG_ENUM, e);
		}

		return type;
	}

	private void setChromeConfig() throws DriverInitException {
		if (StringUtils.isEmpty(chromeDriverPath)) {
			throw new DriverInitException(WEBDRIVER_CHROME_DRIVER_NOT_FOUND);
		}
		System.setProperty(WEBDRIVER_CHROME_DRIVER, chromeDriverPath);
	}

	private void setFirefoxConfig() throws DriverInitException {

		if (StringUtils.isEmpty(geckoDriverPath)) {
			throw new DriverInitException(WEBDRIVER_GECKO_DRIVER_NOT_FOUND);
		}
		if (StringUtils.isEmpty(firefoxDriverPath)) {
			throw new DriverInitException(WEBDRIVER_FIREFOX_BIN_NOT_FOUND);
		}
		System.setProperty(WEBDRIVER_GECKO_DRIVER, geckoDriverPath);
		System.setProperty(WEBDRIVER_FIREFOX_BIN, firefoxDriverPath);
	}

	private String getRemoteHubAddressFromSource() throws DriverInitException {
		if (StringUtils.isEmpty(remoteHub)) {
			throw new DriverInitException(REMOVTE_HUB_NOT_FOUND_IN_PROPERTIES);
		}
		return remoteHub;
	}

	private RemoteWebDriver setRemoteWebDriver(DesiredCapabilities capabilities) throws DriverInitException {
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability("name", "Remote File Upload using Selenium 2's FileDetectors");
		RemoteWebDriver remoteWebDriver = null;
		try {
			remoteWebDriver = new RemoteWebDriver(new URL(getRemoteHubAddressFromSource()), capabilities);
		} catch (MalformedURLException e) {
			throw new DriverInitException(REMOVTE_HUB_CONNECT_ERROR, e);
		}
		remoteWebDriver.setFileDetector(new LocalFileDetector());
		return remoteWebDriver;
	}

	public WebDriver getDriver() throws DriverInitException {
		DriverType driverType = this.loadDriverTypeFromSource();

		WebDriver driver = null;
		DesiredCapabilities capabilities = null;
		switch (driverType) {
		case LOCAL_CHROME:
			this.setChromeConfig();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			driver = new ChromeDriver(options);
			this.browser = Browser.CHROME;
			driver = setBrowserLayout(driver);
			break;
		case REMOTE_CHROME:
			capabilities = DesiredCapabilities.chrome();
			driver = setRemoteWebDriver(capabilities);
			break;
		case LOCAL_FIREFOX:
			this.setFirefoxConfig();
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
			driver = setBrowserLayout(driver);
			this.browser = Browser.FIREFOX;
			break;
		}

		if (driver == null) {
			throw new DriverInitException(DRIVER_IS_NULL);
		}

		driver.manage().timeouts().implicitlyWait(DEFAULT_FIND_ELEMENT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(DEFAULT_PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		
		return driver;
	}

	private WebDriver setBrowserLayout(WebDriver driver) throws DriverInitException {

		WebDriver.Window window = driver.manage().window();
		if (StringUtils.isNotEmpty(browserX) && StringUtils.isNotEmpty(browserY)) {
			window.setPosition(new Point(Integer.valueOf(browserX), Integer.valueOf(browserY)));
		}

		if (StringUtils.isNotEmpty(browserWidth) && StringUtils.isNotEmpty(browserHeigh)) {
			window.setSize(new Dimension(Integer.valueOf(browserWidth), Integer.valueOf(browserHeigh)));
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
