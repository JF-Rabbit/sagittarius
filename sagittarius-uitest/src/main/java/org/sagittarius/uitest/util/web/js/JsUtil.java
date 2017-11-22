package org.sagittarius.uitest.util.web.js;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsUtil implements JsConstant {

    /**
     * 返回页面X原点在显示器的X坐标
     *
     * @param driver
     * @return
     */
    public static int getActualX(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int workAreaWidth = Integer.valueOf(js.executeScript(WINDOW_SCREEN_AVAIL_WIDTH).toString());
        int webDisableWidth = Integer.valueOf(js.executeScript(DOCUMENT_BODY_CLIENT_WIDTH).toString());
        return workAreaWidth - webDisableWidth;
    }

    /**
     * 返回页面Y原点在显示器的Y坐标
     *
     * @param driver
     * @return
     */
    public static int getActualY(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int workAreaHeight = Integer.valueOf(js.executeScript(WINDOW_SCREEN_AVAIL_HEIGHT).toString());
        int webDisableHeight = Integer.valueOf(js.executeScript(DOCUMENT_BODY_CLIENT_HEIGHT).toString());
        return workAreaHeight - webDisableHeight;
    }

    /**
     * 移动Element到顶/底端
     *
     * @param driver
     * @param element
     * @param isToTop true: 移动到顶端 false: 移动到底端
     */
    public static void scrollElement(WebDriver driver, WebElement element, boolean isToTop) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (isToTop) {
            js.executeScript(SCROLL_TO_TOP, element);
        } else {
            js.executeScript(SCROLL_TO_BOTTOM, element);
        }
    }
}
