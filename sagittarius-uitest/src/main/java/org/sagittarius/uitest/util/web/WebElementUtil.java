package org.sagittarius.uitest.util.web;

import org.openqa.selenium.WebElement;

public class WebElementUtil {

	public static int[] getElementCenter(WebElement element) {
		int x = element.getLocation().x;
		int y = element.getLocation().y;
		int height = element.getSize().height;
		int width = element.getSize().width;
		return new int[] { (x + width) / 2, (y + height) / 2 };
	}

}
