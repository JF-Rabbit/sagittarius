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
        return new int[]{x + width / 2, y + height / 2};
    }

    private static final String ELEMENT_NOT_VISIBLE_MSG = "element is not visible:";
    private static final String ELEMENT_IS_VISIBLE_MSG = "element is visible, please inspect:";

    @Deprecated
    public static void clickDisableElement(WebDriver driver, WebElement element) {
        if (!element.isDisplayed()) {
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
    private static final String INPUT_LIST_SIZE_IS_0 = "INPUT_LIST_SIZE_IS_0";
    private static final String FIND_BY_WAIT_WARN = "findByWait timeout should > DriverManager.find_element_timeout";

    public static void clickOneDisplayedElementOfList(List<WebElement> elementList) {
        getOneDisplayedElementOfList(elementList).click();
    }

    public static WebElement getOneDisplayedElementOfList(List<WebElement> elementList) {
        if (elementList.size() == 0) {
            throw new IllegalArgumentException(INPUT_LIST_SIZE_IS_0);
        }
        List<WebElement> list = new ArrayList<>();
        for (int i = 0; i < elementList.size(); i++) {
            logger.info("index:{}, isDisplayed:{}, element:{}", i, elementList.get(i).isDisplayed(), elementList.get(i));
            if (elementList.get(i).isDisplayed()) {
                list.add(elementList.get(i));
            }
        }

        if (list.size() == 0) {
            throw new IllegalArgumentException(NO_DISPLAYED_ELEMENT);
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new IllegalArgumentException(MULTIPLE_CLICKABLE_ELEMENT);
        }
    }

    private static void checkTimeout(int secound) {
        if (secound <= Integer.valueOf(String.valueOf(DriverManager.find_element_timeout))) {
            throw new IllegalArgumentException(FIND_BY_WAIT_WARN);
        }
    }

    private static final String NOT_IMPL = "key is not implement!";

    public static WebElement findElement(WebDriver driver, WebByEnum by, String msg) {

        switch (by) {
            case XPATH:
                return driver.findElement(By.xpath(msg));
            case ID:
                return driver.findElement(By.id(msg));
            case CLASS_NAME:
                return driver.findElement(By.className(msg));
            case NAME:
                return driver.findElement(By.name(msg));
            case TAG_NAME:
                return driver.findElement(By.tagName(msg));
            case LINK_TEXT:
                return driver.findElement(By.linkText(msg));
            case CSS:
                return driver.findElement(By.cssSelector(msg));
            case PARTIA_LINK_TEXT:
                return driver.findElement(By.partialLinkText(msg));
            default:
                throw new IllegalArgumentException(NOT_IMPL);
        }
    }

    public static List<WebElement> findElements(WebDriver driver, WebByEnum by, String msg) {

        switch (by) {
            case XPATH:
                return driver.findElements(By.xpath(msg));
            case ID:
                return driver.findElements(By.id(msg));
            case CLASS_NAME:
                return driver.findElements(By.className(msg));
            case NAME:
                return driver.findElements(By.name(msg));
            case TAG_NAME:
                return driver.findElements(By.tagName(msg));
            case LINK_TEXT:
                return driver.findElements(By.linkText(msg));
            case CSS:
                return driver.findElements(By.cssSelector(msg));
            case PARTIA_LINK_TEXT:
                return driver.findElements(By.partialLinkText(msg));
            default:
                throw new IllegalArgumentException(NOT_IMPL);
        }
    }

    public static WebElement findElementByWait(WebDriver driver, int secound, WebByEnum by, String msg) {
        checkTimeout(secound);
        WebDriverWait wait = new WebDriverWait(driver, secound);
        return wait.until(d -> findElement(d, by, msg));
    }

    public static List<WebElement> findElementsByWait(WebDriver driver, int secound, WebByEnum by, String msg) {
        checkTimeout(secound);
        WebDriverWait wait = new WebDriverWait(driver, secound);
        return wait.until(d -> findElements(d, by, msg));
    }

    public static void saveCurrentWebPage(String path) {
        RobotUtil.setClipboardData(path);
        RobotUtil.action_CONTROL_S_CONTROL_V_ENTER();
    }

    public static boolean isExsit(WebDriver driver, WebByEnum by, String msg) {
        try {
            WebElement element = findElement(driver, by, msg);
            if (element.isDisplayed()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
