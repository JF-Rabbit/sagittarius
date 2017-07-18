package org.sagittarius.uitest.util.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sagittarius.common.robot.RobotUtil;
import org.sagittarius.uitest.util.web.js.JsUtil;

public class WebElementUtil {

	public static int[] getElementCenter(WebElement element) {
		int x = element.getLocation().x;
		int y = element.getLocation().y;
		int height = element.getSize().height;
		int width = element.getSize().width;
		return new int[] { (x + width) / 2, (y + height) / 2 };
	}

	public static void clickDisableElement(WebDriver driver, WebElement element) {
		int[] coordinate = getElementCenter(element);
		RobotUtil.moveToClick(coordinate[0], coordinate[1] + JsUtil.getActualY(driver));
	}

}
