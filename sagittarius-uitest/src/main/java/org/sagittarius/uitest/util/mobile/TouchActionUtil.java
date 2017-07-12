package org.sagittarius.uitest.util.mobile;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class TouchActionUtil {
	public static void swipe(AppiumDriver<?> driver, int startx, int starty, int endx, int endy, int duration) {
		if (driver instanceof AndroidDriver) {
			new TouchAction(driver).press(startx, starty).waitAction(changeMillisToDuration(duration)).moveTo(endx, endy).release()
					.perform();
		}
		if (driver instanceof IOSDriver) {
			int xOffset = endx - startx;
			int yOffset = endy - starty;
			new TouchAction(driver).press(startx, starty).waitAction(changeMillisToDuration(duration)).moveTo(xOffset, yOffset).release()
					.perform();
		}
	}

	public static void tap(AppiumDriver<?> driver, int x, int y) {
		TouchAction tap = new TouchAction(driver);
		tap.press(x, y).release().perform();
	}

	/**
	 * will support on java client BETA-7 or later
	 * 
	 * @param duration
	 * @return
	 */
	private static Duration changeMillisToDuration(int duration) {
		return Duration.ofMillis(duration);
	}
}
