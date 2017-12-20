package org.sagittarius.uitest.util.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.sagittarius.uitest.exception.BrowserConsoleErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserUtil {

    private static final Logger logger = LoggerFactory.getLogger(BrowserUtil.class);

    /**
     * 浏览器返回上一层
     */
    public static void back(WebDriver driver) {
        driver.navigate().back();
    }

    /**
     * 浏览器前进
     */
    public static void forward(WebDriver driver) {
        driver.navigate().forward();
    }

    /**
     * 浏览器弹框确定
     */
    public static void accept(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    /**
     * 浏览器弹框取消
     */
    public static void dismiss(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    /**
     * 浏览器弹框信息
     */
    public static String alertText(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    /**
     * 浏览器控制台是否报错
     */
    public void checkBrowserHaveErrorLog(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            logger.info(entry.getLevel().toString());
            if (entry.getLevel().toString().equals("ERROR")) {
                logger.error("CONSOLE MSG:{} {}", entry.getLevel(), entry.getMessage());
                throw new BrowserConsoleErrorException(entry.getMessage());
            }
        }
    }

}
