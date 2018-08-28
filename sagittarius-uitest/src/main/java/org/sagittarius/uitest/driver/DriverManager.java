package org.sagittarius.uitest.driver;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.sagittarius.uitest.exception.DriverInitException;
import org.sagittarius.uitest.util.web.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Component
@PropertySource({DriverConstant.SELENIUM_CONFIG_PATH})
public class DriverManager implements DriverConstant {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

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

    @Value(SELENIUM_FIND_ELEMENT_TIMEOUT)
    public Object findElementTimeout;

    @Value(SELENIUM_PAGE_LOAD_TIMEOUT)
    public Object loadPageTimeout;

    private Browser browser;

    public Browser getBrowser() {
        return browser;
    }

    private List<String> consoleIgnores;

    public List<String> getConsoleIgnores() {
        return consoleIgnores;
    }

    public enum DriverType {
        LOCAL_CHROME, REMOTE_CHROME, LOCAL_FIREFOX,
    }

    private DriverType loadDriverTypeFromSource() throws DriverInitException {

        if (StringUtils.isEmpty(driverType)) {
            throw new DriverInitException(DRIVER_TYPE_NOT_FOUND_IN_PROPERTIES);
        }

        DriverType type;
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
        RemoteWebDriver remoteWebDriver;
        try {
            remoteWebDriver = new RemoteWebDriver(new URL(getRemoteHubAddressFromSource()), capabilities);
        } catch (MalformedURLException e) {
            throw new DriverInitException(REMOVTE_HUB_CONNECT_ERROR, e);
        }
        remoteWebDriver.setFileDetector(new LocalFileDetector());
        return remoteWebDriver;
    }

    public static int find_element_timeout = DEFAULT_SELENIUM_FIND_ELEMENT_TIMEOUT;

    private int initFindElementTimeout() {
        if (findElementTimeout != null) {
            find_element_timeout = Integer.valueOf(String.valueOf(findElementTimeout));
            return find_element_timeout;
        }
        return DEFAULT_SELENIUM_FIND_ELEMENT_TIMEOUT;
    }

    private int initSeleniumPageLoadTimeout() {
        if (loadPageTimeout != null) {
            return Integer.valueOf(String.valueOf(loadPageTimeout));
        }
        return DEFAULT_SELENIUM_PAGE_LOAD_TIMEOUT;
    }

    private WebDriver driver;

    public WebDriver getDriver() {
        return this.driver;
    }

    @PostConstruct
    public void initDriverByOptions() throws DriverInitException {
        DriverType driverType = this.loadDriverTypeFromSource();
        switch (driverType) {
            case LOCAL_CHROME:
                this.setChromeConfig();

                ChromeOptions options = new ChromeOptions();

                setChromeOptions(options);

                driver = new ChromeDriver(options);

                this.browser = Browser.CHROME;
                driver = setBrowserLayout(driver);
                break;
//            case REMOTE_CHROME:
//                capabilities = DesiredCapabilities.chrome();
//                driver = setRemoteWebDriver(capabilities);
//                break;
//            case LOCAL_FIREFOX:
//                this.setFirefoxConfig();
//                // skip SSL
//                // ProfilesIni profilesIni = new ProfilesIni();
//                // FirefoxProfile profile = profilesIni.getProfile("default");
//                // profile.setAcceptUntrustedCertificates(true);
//                // profile.setAssumeUntrustedCertificateIssuer(true);
//
//                // FirefoxOptions options = new FirefoxOptions();
//                // options.setProfile(profile);
//
//                capabilities = DesiredCapabilities.firefox();
//
//                driver = new FirefoxDriver(capabilities);
//            /*
//             * XXX can't set maximize for firefox
//			 *
//			 * use maximize history manually to skip
//			 *
//			 * github bug: https://github.com/mozilla/geckodriver/issues/820
//			 *
//			 */
//                driver = setBrowserLayout(driver);
//                this.browser = Browser.FIREFOX;
//                break;
        }

        if (driver == null) {
            throw new DriverInitException(DRIVER_IS_NULL);
        }

        consoleIgnores = new ArrayList<>();

        driver.manage().timeouts().implicitlyWait(initFindElementTimeout(), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(initSeleniumPageLoadTimeout(), TimeUnit.SECONDS);

    }

    private void setChromeOptions(ChromeOptions options) {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        Map<String, Object> chromePrefs = new HashMap<>();
        // 允许下载多个文件
        chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);

        // 下载不弹框，指定下载路径
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", new File(DOWNLOAD_PATH).getAbsolutePath());
        chromePrefs.put("download.directory_upgrade", true);

        options.setExperimentalOption("prefs", chromePrefs);

        // 取消脚本警告提醒
        options.addArguments("disable-infobars");

        options.setCapability(ChromeOptions.CAPABILITY, options);
    }

    private WebDriver setBrowserLayout(WebDriver driver) {

        WebDriver.Window window = driver.manage().window();
        if (StringUtils.isNotEmpty(browserX) && StringUtils.isNotEmpty(browserY)) {
            window.setPosition(new Point(Integer.valueOf(browserX), Integer.valueOf(browserY)));
        }

        if (StringUtils.isNotEmpty(browserWidth) && StringUtils.isNotEmpty(browserHeigh)) {
            window.setSize(new Dimension(Integer.valueOf(browserWidth), Integer.valueOf(browserHeigh)));
        } else {
            // test pass on Firefox 57.0.2
//            if (browser != Browser.CHROME) {
//                throw new DriverInitException(
//                        "can't set maximize for firefox, github bug: https://github.com/mozilla/geckodriver/issues/820");
//            }
            window.maximize();
        }

        return driver;
    }

    @PreDestroy
    public void quitDriver() {
        logger.info("Destroy Driver...");
        if (this.driver == null) {
            logger.info("Destroy Failed! Driver is null!");
        } else {
            this.driver.quit();
            logger.info("Destroy Done");
        }
    }
}
