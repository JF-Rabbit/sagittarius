package org.sagittarius.uitest;

import org.openqa.selenium.WebDriver;
import org.sagittarius.uitest.driver.DriverManager;
import org.sagittarius.uitest.exception.DriverInitException;
import org.sagittarius.uitest.util.PageElementUtil;
import org.sagittarius.uitest.util.web.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@ContextConfiguration(locations = CommonConstant.SPRING_PATH)
public class WebTest extends AbstractTestNGSpringContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(WebTest.class);
	private static final String TEST_START = "========== TEST_START ==========\n";
	private static final String TEST_END = "========== TEST_END ==========\n";
	
	public Browser currentBrowser;

	@Resource
	private DriverManager manager;
	public WebDriver driver;

	@BeforeClass
	public void Start() {
		logger.info(TEST_START);
	}

	@BeforeMethod
	public void setup(Method method) throws DriverInitException {
		logger.info("Init Driver...");
		manager.initDriver();
		driver = manager.getDriver();
		currentBrowser = manager.getBrowser();
		logger.info("Init Done");
		logger.info("Case: " + method.getName() + " Start...");
	}

	@AfterMethod
	public void teardown(Method method) {
		logger.info("Case: " + method.getName() + " End...");
		logger.info("Destroy Driver...");
		if (driver == null) {
			logger.info("Destroy Failed! Driver is null!");
		} else {
			manager.quitDriver(driver);
			logger.info("Destroy Done");
		}
	}

	@AfterClass(alwaysRun = true)
	public void end() {
		logger.info(TEST_END);
	}

	public void getSource() {
		PageElementUtil.showPageSource(driver);
	}
}
