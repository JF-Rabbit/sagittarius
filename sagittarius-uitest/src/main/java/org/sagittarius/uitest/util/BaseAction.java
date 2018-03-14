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

    private DriverManager manager;

    public DriverManager getManager() {
        return manager;
    }

    public void setManager(DriverManager manager) {
        this.manager = manager;
    }

    public WebDriver driver() {
        return this.manager.getDriver();
    }

    public void open(String url) {
        logger.info("Open URL: {}", url);
        manager.getDriver().get(url);
    }

    public void sleep(int millisecond) {
        logger.info("Sleep: {} ms", millisecond);
        try {
            Thread.sleep(millisecond);
        } catch (Exception e) {
            //
        }
    }

    public void sleep() {
        sleep(500);
    }

    public WebElement find(By by) {
        logger.info("Find Element By: {}", by);
        return manager.getDriver().findElement(by);
    }

    public List<WebElement> finds(By by) {
        logger.info("Find Elements By: {}", by);
        return manager.getDriver().findElements(by);
    }

    public void script(String script, Object... args) {
        logger.info("Execute Script: {}, args: {}", script, args);
        JavascriptExecutor js = (JavascriptExecutor) manager.getDriver();
        js.executeScript(script, args);
    }

    public void click(WebElement element) {
        logger.info("Click Element: {}", element);
        element.click();
        sleep();
    }

    public void click(WebElement element, int millisecond) {
        element.click();
        sleep(millisecond);
    }

    public void sendKeys(WebElement element, String keys) {
        logger.info("Send Keys: {} to Element: {}", keys, element);
        element.sendKeys(keys);
    }

    public void c_sendKeys(WebElement element, String keys) {
        click(element);
        element.clear();
        element.sendKeys(keys);
    }

    public void source() {
        logger.info(manager.getDriver().getPageSource());
    }

    public void initPages(PageUI... pageUIs) {
        for (PageUI pageUI : pageUIs) {
            logger.info("Init Page;{} ", pageUI);
            PageFactory.initElements(manager.getDriver(), pageUI);
        }
    }

    public List<WebElement> loadList(By by, int timeout) {
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

    public WebElement wait(By by, int secound) {
        checkTimeout(secound);
        WebDriverWait wait = new WebDriverWait(manager.getDriver(), secound);
        return wait.until(d -> find(by));
    }

}
