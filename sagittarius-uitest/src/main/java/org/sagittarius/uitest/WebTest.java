package org.sagittarius.uitest;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.sagittarius.uitest.driver.DriverManager;
import org.sagittarius.uitest.exception.DriverInitException;
import org.sagittarius.uitest.util.PageElementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = CommonConstant.SPRING_PATH)
public class WebTest extends AbstractJUnit4SpringContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(WebTest.class);
	private static final String TEST_START = "========== TEST_START ==========\n";
	private static final String TEST_END = "========== TEST_END ==========\n";

	@Resource
	DriverManager manager;
	public WebDriver driver;

	@Before
	public void setup() throws DriverInitException {
		logger.info(TEST_START);
		driver = manager.getDriver();
	}

	@After
	public void teardown() {
		manager.quitDriver(driver);
		logger.info(TEST_END);
	}

	public void getSource() {
		PageElementUtil.showPageSource(driver);
	}
}
