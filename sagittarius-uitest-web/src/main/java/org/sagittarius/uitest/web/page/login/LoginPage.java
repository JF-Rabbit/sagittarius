package org.sagittarius.uitest.web.page.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.web.page.CommonPage;

public class LoginPage extends CommonPage {

	@FindBy(id = "username")
	public WebElement usernameInput;
	@FindBy(id = "password")
	public WebElement passwordInput;
	@FindBy(xpath = "//input[@value='登录']")
	public WebElement loginBtn;

}