package org.sagittarius.uitest.web.action;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.sagittarius.common.judge.JudgeUtil;
import org.sagittarius.common.map.MapUtil;
import org.sagittarius.common.yaml.YamlUtil;
import org.sagittarius.uitest.util.PageElementUtil;
import org.sagittarius.uitest.web.ConfigConstant;
import org.sagittarius.uitest.web.enventity.Environment;
import org.sagittarius.uitest.web.page.login.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginAction {

	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

	private Environment environment;

	public LoginAction() {
		this.environment = getEnv();
		if (JudgeUtil.isObjHaveNullField(environment)) {
			throw new RuntimeException("Environment args config error!");
		}
	}

	@SuppressWarnings("unchecked")
	private Environment getEnv() {
		Map<String, Object> yamlFile = (Map<String, Object>) YamlUtil.load(ConfigConstant.ENV_CONFIG_PATH);
		Map<String, Object> map = (Map<String, Object>) yamlFile.get(yamlFile.get(ConfigConstant.ENV));
		return MapUtil.map2Obj(Environment.class, map);
	}

	public void login(WebDriver driver, String username, String password) {
		driver.get(environment.getUrl());
		LoginPage loginPage = new LoginPage();
		PageElementUtil.initPages(driver, loginPage);
		loginPage.usernameInput.sendKeys(username);
		loginPage.passwordInput.sendKeys(password);
		loginPage.loginBtn.click();
	}

	public void login(WebDriver driver) {
		driver.get(environment.getUrl());
		try {
			LoginPage loginPage = new LoginPage();
			PageElementUtil.initPages(driver, loginPage);
			loginPage.usernameInput.sendKeys(environment.getUsername());
			loginPage.passwordInput.sendKeys(environment.getPassword());
			loginPage.loginBtn.click();
		} catch (NoSuchElementException e) {
			logger.warn("Not Login");
		}
	}

	public void loadTask(WebDriver driver) {
		driver.findElement(By.xpath("//*[@id=\"content-layout\"]/div/div/div/a/button/span")).click();
	}

	public void inputTaskName(WebDriver driver, String taskName) {
		driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(taskName);
	}

	@Deprecated
	public void clickUpload(WebDriver driver) {
		driver.findElement(By
				.xpath("//*[@id=\"content-layout\"]/div/div/form/section[1]/div/div[3]/div[2]/div/span/div[1]/span/button"))
				.click();
	}

	public void sendFile(WebDriver driver, String filePath) {
		driver.findElement(By
				.xpath("//*[@id=\"content-layout\"]/div/div/form/section[1]/div/div[3]/div[2]/div/span/div[1]/span/input"))
				.sendKeys(filePath);
	}

	@Deprecated
	public void setClipboardData(String filePath) {
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

	@Deprecated
	public void selectFile(String filePath) throws AWTException, InterruptedException {
		// if (filePath.endsWith("/")) {
		// filePath =
		// filePath.replace(Character.toString(filePath.charAt(filePath.length()
		// - 1)), "");
		// }
		// System.out.println(filePath);
		setClipboardData(filePath);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
	}

	public void clickLoadBtn(WebDriver driver) {
		driver.findElement(By.xpath("//*[@id=\"content-layout\"]/div/div/form/section[2]/button/span")).click();
	}

}
