package org.sagittarius.uitest;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.sagittarius.uitest.driver.DriverManager;
import org.sagittarius.uitest.exception.DriverInitException;
import org.sagittarius.uitest.util.PageElementUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = CommonConstant.SPRING_PATH)
public class WebTest extends AbstractJUnit4SpringContextTests {

	@Resource
	DriverManager manager;
	public WebDriver driver;

	@Before
	public void setup() throws DriverInitException, IOException {
		driver = manager.getDriver();
	}

	@After
	public void teardown() {
		manager.quitDriver(driver);
	}
	
	public void getSource() {
		PageElementUtil.showPageSource(driver);
	}
}
