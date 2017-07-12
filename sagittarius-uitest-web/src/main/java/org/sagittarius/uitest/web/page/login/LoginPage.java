package org.sagittarius.uitest.web.page.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class LoginPage implements PageUI {

	@FindBy(xpath = "//*[@id=\"username\"]")
	public WebElement usernameInput;
	@FindBy(xpath = "//*[@id=\"password\"]")
	public WebElement passwordInput;
	@FindBy(xpath = "//*[@id=\"fm1\"]/section[4]/input[4]")
	public WebElement loginBtn;

}