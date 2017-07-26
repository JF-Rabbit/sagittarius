package org.sagittarius.uitest.web.test.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sagittarius.common.Delay;
import org.testng.annotations.Test;

public class TestFirefoxDemo {

	@Test
	public void test() {
		System.setProperty("webdriver.gecko.driver", "D:/project/selenium/firefox/v18/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "D:/program/Mozilla Firefox/firefox.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.baidu.com");
		Delay.suspend();
	}

}
