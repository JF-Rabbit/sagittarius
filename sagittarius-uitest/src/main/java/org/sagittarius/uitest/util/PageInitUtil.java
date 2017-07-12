package org.sagittarius.uitest.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageInitUtil {

	public static void initPages(WebDriver driver, PageUI... pageUIs) {
		for (PageUI pageUI : pageUIs) {
			PageFactory.initElements(driver, pageUI);
		}
	}

}
