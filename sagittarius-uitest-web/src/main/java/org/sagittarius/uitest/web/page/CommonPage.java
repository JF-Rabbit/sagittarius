package org.sagittarius.uitest.web.page;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class CommonPage implements PageUI {
	@FindBy(xpath = "//span[text()='取 消']")
	public WebElement cancelBtn;

	@FindBy(xpath = "//span[text()='确 定']")
	public WebElement uniqeConfirmBtn;

	@FindAll(@FindBy(xpath = "//span[text()='确 定']"))
	public List<WebElement> allConfirmBtn;
	
	@FindBy(xpath = "//div[text()='请选择']")
	public WebElement singleSelectDiv;
	
	@FindAll(@FindBy(xpath = "//div[text()='请选择']"))
	public List<WebElement> multiSelectDiv;
}
