package org.sagittarius.uitest.util.web.js;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JsUtil implements JsConstant {

	/**
	 * 获取页面显示的实际X坐标
	 * 
	 * @param driver
	 * @return
	 */
	public static int getActualX(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int workAreaHeight = Integer.valueOf(js.executeScript(WINDOW_SCREEN_AVAILHEIGHT).toString());
		int webDisableHeight = Integer.valueOf(js.executeScript(DOCUMENT_BODY_CLIENTHEIGHT).toString());
		return workAreaHeight - webDisableHeight;
	}

}
