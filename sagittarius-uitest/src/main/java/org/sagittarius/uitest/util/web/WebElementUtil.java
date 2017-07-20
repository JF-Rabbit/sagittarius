package org.sagittarius.uitest.util.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sagittarius.common.robot.RobotUtil;
import org.sagittarius.uitest.driver.DriverManager;
import org.sagittarius.uitest.util.web.js.JsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebElementUtil {

	private static final Logger logger = LoggerFactory.getLogger(WebElementUtil.class);

	public static int[] getElementCenter(WebElement element) {
		int x = element.getLocation().x;
		int y = element.getLocation().y;
		int height = element.getSize().height;
		int width = element.getSize().width;
		logger.info("x:{}, y:{}, height:{}, width:{}", x, y, height, width);
		return new int[] { x + width / 2, y + height / 2 };
	}

	private static final String ELEMENT_NOT_VISIBLE_MSG = "element is not visible:";
	private static final String ELEMENT_IS_VISIBLE_MSG = "element is visible, please inspect:";

	public static void clickDisableElement(WebDriver driver, WebElement element) {
		if (element.isDisplayed() == false) {
			logger.warn(ELEMENT_NOT_VISIBLE_MSG + "{}", element);
			// PageElementUtil.showPageSource(driver);
		} else {
			logger.warn(ELEMENT_IS_VISIBLE_MSG + "{}", element);
		}
		int[] coordinate = getElementCenter(element);
		logger.info("screenX:{}, screenY:{}", coordinate[0], coordinate[1] + JsUtil.getActualY(driver));
		RobotUtil.moveToClick(coordinate[0], coordinate[1] + JsUtil.getActualY(driver));
	}

	private static final String MULTIPLE_CLICKABLE_ELEMENT = "MULTIPLE_CLICKABLE_ELEMENT";
	private static final String NO_DISPLAYED_ELEMENT = "NO_DISPLAYED_ELEMENT";

	public static void clickOneDisplayedElementofList(List<WebElement> elementList) {
		List<WebElement> list = new ArrayList<WebElement>();
		for (int i = 0; i < elementList.size(); i++) {
			logger.info("index:{}, isDisplayed:{}, element:{}", i, elementList.get(i).isDisplayed(), elementList.get(i));
			if (elementList.get(i).isDisplayed()) {
				list.add(elementList.get(i));
			}
		}

		if (list.size() == 0) {
			throw new IllegalArgumentException(NO_DISPLAYED_ELEMENT);
		} else if (list.size() == 1) {
			list.get(0).click();
		} else {
			throw new IllegalArgumentException(MULTIPLE_CLICKABLE_ELEMENT);
		}

	}

	private static void checkTimeout(int waitSecound) {
		if (waitSecound <= DriverManager.DEFAULT_FIND_ELEMENT_TIMEOUT) {
			throw new IllegalArgumentException(MULTIPLE_CLICKABLE_ELEMENT);
		}
	}

	public static WebElement findElementByWait(WebDriver driver, int waitSecound, FindWebElementEnum findType, String findMsg) {

		checkTimeout(waitSecound);

		WebDriverWait wait = new WebDriverWait(driver, waitSecound);
		return wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				switch (findType) {
				case X_PATH:
					return driver.findElement(By.xpath(findMsg));
				case ID:
					return driver.findElement(By.id(findMsg));
				case CLASS_NAME:
					return driver.findElement(By.className(findMsg));
				case NAME:
					return driver.findElement(By.name(findMsg));
				case TAG_NAME:
					return driver.findElement(By.tagName(findMsg));
				case LINK_TEXT:
					return driver.findElement(By.linkText(findMsg));
				case CSS:
					return driver.findElement(By.cssSelector(findMsg));
				case PARTIA_LINK_TEXT:
					return driver.findElement(By.partialLinkText(findMsg));
				}
				return null;
			}
		});
	}

	public static List<WebElement> findElementsByWait(WebDriver driver, int waitSecound, FindWebElementEnum findType, String findMsg) {

		checkTimeout(waitSecound);

		WebDriverWait wait = new WebDriverWait(driver, waitSecound);
		return wait.until(new ExpectedCondition<List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				switch (findType) {
				case X_PATH:
					return driver.findElements(By.xpath(findMsg));
				case ID:
					return driver.findElements(By.id(findMsg));
				case CLASS_NAME:
					return driver.findElements(By.className(findMsg));
				case NAME:
					return driver.findElements(By.name(findMsg));
				case TAG_NAME:
					return driver.findElements(By.tagName(findMsg));
				case LINK_TEXT:
					return driver.findElements(By.linkText(findMsg));
				case CSS:
					return driver.findElements(By.cssSelector(findMsg));
				case PARTIA_LINK_TEXT:
					return driver.findElements(By.partialLinkText(findMsg));
				}
				return null;
			}
		});
	}

	public static void saveCurrentWebPage(String savePath) {
		RobotUtil.setClipboardData(savePath);
		RobotUtil.action_CONTROL_S_CONTROL_V_ENTER();
	}

}
