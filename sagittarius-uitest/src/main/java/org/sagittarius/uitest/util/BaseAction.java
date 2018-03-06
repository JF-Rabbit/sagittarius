package org.sagittarius.uitest.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sagittarius.uitest.driver.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseAction {

    private static Logger logger = LoggerFactory.getLogger(BaseAction.class);

    protected static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        BaseAction.driver = driver;
    }

    protected static void open(String url) {
        logger.info("Open URL: {}", url);
        driver.get(url);
    }

    protected static void sleep(int millisecond) {
        logger.info("Sleep: {} ms", millisecond);
        try {
            Thread.sleep(millisecond);
        } catch (Exception e) {
            //
        }
    }

    protected static void sleep() {
        sleep(500);
    }

    protected static WebElement find(By by) {
        logger.info("Find Element By: {}", by);
        return driver.findElement(by);
    }

    protected static List<WebElement> finds(By by) {
        logger.info("Find Elements By: {}", by);
        return driver.findElements(by);
    }

    protected static void script(String script, Object... args) {
        logger.info("Execute Script: {}, args: {}", script, args);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, args);
    }

    protected static void click(WebElement element) {
        logger.info("Click Element: {}", element);
        element.click();
        sleep();
    }

    protected static void click(WebElement element, int millisecond) {
        element.click();
        sleep(millisecond);
    }

    protected static void sendKeys(WebElement element, String keys) {
        logger.info("Send Keys: {} to Elements: {}", keys, element);
        element.sendKeys(keys);
    }

    protected static void c_sendKeys(WebElement element, String keys) {
        click(element);
        element.clear();
        element.sendKeys(keys);
    }

    protected static void source() {
        logger.info(driver.getPageSource());
    }

    public static void initPages(PageUI... pageUIs) {
        for (PageUI pageUI : pageUIs) {
            logger.info("Init Page;{} ", pageUI);
            PageFactory.initElements(driver, pageUI);
        }
    }

    public static List<WebElement> loadList(By by, int timeout) {
        while (true) {
            if (timeout < 0) {
                break;
            }
            List<WebElement> list = finds(by);
            if (list.size() > 0) {
                return list;
            }
            sleep(980);
            timeout--;
        }
        return null;
    }

    private static final String FIND_BY_WAIT_WARN = "findByWait timeout should > DriverManager.find_element_timeout";

    private static void checkTimeout(int secound) {
        if (secound <= Integer.valueOf(String.valueOf(DriverManager.find_element_timeout))) {
            throw new IllegalArgumentException(FIND_BY_WAIT_WARN);
        }
    }

    public static WebElement wait(By by, int secound) {
        checkTimeout(secound);
        WebDriverWait wait = new WebDriverWait(driver, secound);
        return wait.until(d -> find(by));
    }

}
