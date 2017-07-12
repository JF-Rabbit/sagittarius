package org.sagittarius.uitest.web.test;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.sagittarius.uitest.driver.DriverManager;
import org.sagittarius.uitest.exception.DriverInitException;
import org.sagittarius.uitest.web.action.LoginAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TestKMXDemo extends AbstractJUnit4SpringContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(TestKMXDemo.class);

	@Resource
	DriverManager manager;
	WebDriver driver;

	String url = "http://192.168.130.101:5004";

	@Before
	public void setup() throws DriverInitException {
		driver = manager.getDriver();
		driver.get(url);
		logger.info("url:{}, driver:{}", url, driver);
		logger.warn("url:{}, driver:{}", url, driver);
		logger.error("url:{}, driver:{}", url, driver);
		
	}

	@After
	public void teardown() {
		manager.quitDriver(driver);
	}

	String username = "k2data";
	String password = "K2Data@k001";
	String filePath = "D:\\project\\selenium\\chromedriver\\v2.9\\1.txt";

	@Resource
	LoginAction loginAction;
	
	@Test
	public void test01() throws IOException {

		loginAction.login(driver, username, password);
		loginAction.loadTask(driver);
		loginAction.inputTaskName(driver, "任务6");
		
		loginAction.sendFile(driver, filePath);
		
		// loginAction.clickLoadBtn(driver);
		// TODO 点击上传后加延迟判断，等待上传完成
		System.in.read();
	}
}
