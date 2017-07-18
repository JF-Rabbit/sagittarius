package org.sagittarius.uitest.web.page.dataAnalysis;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class CreateProjectInfoPage implements PageUI {

	@FindBy(id = "projName")
	public WebElement projectNameInput;
	
	@FindBy(id = "description")
	public WebElement projectDescInput;
	
	@FindBy(xpath = "//span[text()='取 消']")
	public WebElement projectCancelBtn;
	
	@FindBy(xpath = "//span[text()='确 定']")
	public WebElement projectConfirmBtn;

}
