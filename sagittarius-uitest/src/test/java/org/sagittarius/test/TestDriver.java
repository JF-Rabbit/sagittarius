package org.sagittarius.test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author JasonZhang 2018/8/28 下午2:57
 */
public class TestDriver {

    public static void main(String[] args) throws InterruptedException {
        String path = "/Users/jasonzhang/Documents/code/sagittarius/sagittarius-uitest/src/main/resources/webdriver/chrome/v2.35/mac/chromedriver";

        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();


        Map<String, Object> chromePrefs = new HashMap<>();
        // 允许下载多个文件
        chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);

        // 下载不弹框，指定下载路径
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", new File("config/download").getAbsolutePath());
        chromePrefs.put("download.directory_upgrade", true);

        options.setExperimentalOption("prefs", chromePrefs);

        // 取消脚本警告提醒
        options.addArguments("disable-infobars");

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        options.setCapability(ChromeOptions.CAPABILITY, options);

        ChromeDriver driver = new ChromeDriver(options);

        driver.get("http://www.baidu.com");
        Thread.sleep(3000);
        driver.quit();
    }
}
