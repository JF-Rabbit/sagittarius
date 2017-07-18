package org.sagittarius.uitest.web.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class CommonPage implements PageUI {
	@FindBy(xpath = "//span[text()='取 消']")
	public WebElement cancelBtn;

	@FindBy(xpath = "//span[text()='确 定']")
	public WebElement confirmBtn;
}
