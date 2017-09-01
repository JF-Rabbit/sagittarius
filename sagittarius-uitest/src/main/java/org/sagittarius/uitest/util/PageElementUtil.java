package org.sagittarius.uitest.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITBy;

public class PageElementUtil {

	private static final Logger logger = LoggerFactory.getLogger(PageElementUtil.class);

	public static void initPages(WebDriver driver, PageUI... pageUIs) {
		for (PageUI pageUI : pageUIs) {
			PageFactory.initElements(driver, pageUI);
		}
	}

	public static Map<String, Object> getUnFindElement(WebDriver driver, PageUI... pageUIs)
			throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> unFindElementMap = new HashMap<String, Object>();
		initPages(driver, pageUIs);
		for (PageUI pageUI : pageUIs) {
			Field[] fields = pageUI.getClass().getFields();
			for (Field field : fields) {
				field.setAccessible(true);
				// TODO add more annotation
				if (field.isAnnotationPresent(FindBy.class) || field.isAnnotationPresent(AndroidFindBy.class)
						|| field.isAnnotationPresent(iOSXCUITBy.class)) {
					// TODO need test
					if (field.get(pageUI) == null) {
						unFindElementMap.put(pageUI.getClass().getSimpleName(), field.getName());
					}
				}
			}
		}
		return unFindElementMap;
	}

	public static void sendKey(WebElement element, String key, boolean isClick, boolean isClear) {
		if (isClick)
			element.click();

		if (isClear)
			element.clear();

		element.sendKeys(key);
	}

	public static void showPageSource(WebDriver driver) {
		logger.info(driver.getPageSource());
	}

}
