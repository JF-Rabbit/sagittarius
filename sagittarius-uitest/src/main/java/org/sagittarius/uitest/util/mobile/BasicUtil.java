package org.sagittarius.uitest.util.mobile;

import io.appium.java_client.AppiumDriver;

public class BasicUtil {

	/** 获取屏幕高度 */
	public static int getScreenHeight(AppiumDriver<?> driver) {
		return driver.manage().window().getSize().height;
	}

	/** 获取屏幕宽度 */
	public static int getScreenWidth(AppiumDriver<?> driver) {
		return driver.manage().window().getSize().width;
	}

}
