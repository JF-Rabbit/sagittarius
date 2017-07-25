package org.sagittarius.uitest.util.web;

import org.openqa.selenium.WebDriver;

public class BrowserUtil {

	/** 浏览器返回上一层 */
	public static void back(WebDriver driver) {
		driver.navigate().back();
	}

	/** 浏览器前进 */
	public static void forward(WebDriver driver) {
		driver.navigate().forward();
	}

	/** 浏览器弹框确定 */
	public static void accept(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	/** 浏览器弹框取消 */
	public static void dismiss(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

}
