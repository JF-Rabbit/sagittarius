package org.sagittarius.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.sagittarius.common.properties.PropertiesUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class WebDriverConsoleManager {

    private WebDriver driver;

    public static void main(String[] args) throws IOException {
        WebDriverConsoleManager manager = new WebDriverConsoleManager();
        manager.initDriver();
        Scanner scanner = new Scanner(System.in);
        String line;
        Map<String, WebElement> elements = new HashMap<>();
        System.out.println("=============================");
        while (true) {
            try {
                line = scanner.nextLine();
                String[] params = line.split(" ");
                switch (Command.valueOf(params[0].toUpperCase())) {
                    case G:
                        manager.driver.get(params[1]);
                        break;
                    case F:
                        WebElement element = null;
                        String msg = "";
                        switch (Find.valueOf(params[1].toUpperCase())) {
                            case ID:
                                element = manager.driver.findElement(By.id(params[2]));
                                msg = "id:" + params[2];
                                break;
                            case XPATH:
                                element = manager.driver.findElement(By.xpath(params[2]));
                                msg = "xpath:" + params[2];
                                break;
                            case CLASSNAME:
                                element = manager.driver.findElement(By.className(params[2]));
                                msg = "className:" + params[2];
                                break;
                            default:
                                break;
                        }
                        System.out.println(element);
                        System.out.println(msg);
                        elements.put(msg, element);
                        break;
                    case S:
                        WebElement sendkey_element = elements.get(params[1]);
                        sendkey_element.sendKeys(params[2]);
                        break;
                    case C:
                        WebElement click_element = elements.get(params[1]);
                        click_element.click();
                        break;
                    case Q:
                        manager.driver.close();
                        return;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initDriver() throws IOException {
        String selenium_config_file = WebDriverConsoleManager.class.getClassLoader().getResource("config/selenium.properties").getPath();
        Properties properties = PropertiesUtil.load(selenium_config_file);
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    enum Command {
        G, F, S, C, Q
    }

    enum Find {
        ID, XPATH, CLASSNAME
    }

}
