package org.sagittarius.uitest;

import java.lang.reflect.Method;

import javax.annotation.Resource;

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

@ContextConfiguration(locations = CommonConstant.SPRING_PATH)
public class WebTest extends AbstractTestNGSpringContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(WebTest.class);
	private static final String TEST_START = "========== TEST_START ==========\n";
	private static final String TEST_END = "========== TEST_END ==========\n";
	
	public Browser currentBrowser;

	@Resource
	DriverManager manager;
	public WebDriver driver;

	@BeforeClass
	public void Start() {
		logger.info(TEST_START);
	}

	@BeforeMethod
	public void setup(Method method) throws DriverInitException {
		logger.info("Init Driver...");
		driver = manager.getDriver();
		currentBrowser = manager.getBrowser();
		logger.info("Init Done");
		logger.info("Case: " + method.getName() + " Start...");
	}

	@AfterMethod
	public void teardown(Method method) {
		logger.info("Case: " + method.getName() + " End...");
		logger.info("Destory Driver...");
		manager.quitDriver(driver);
		logger.info("Destory Done");
	}

	@AfterClass(alwaysRun = true)
	public void end() {
		logger.info(TEST_END);
	}

	public void getSource() {
		PageElementUtil.showPageSource(driver);
	}
}
