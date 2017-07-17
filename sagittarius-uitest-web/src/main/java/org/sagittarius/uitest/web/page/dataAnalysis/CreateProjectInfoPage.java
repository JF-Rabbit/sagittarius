package org.sagittarius.uitest.web.page.dataAnalysis;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class CreateProjectInfoPage implements PageUI {

	@FindBy(xpath = "//*[@id=\"projName\"]")
	public WebElement projectNameInput;
	
	@FindBy(xpath = "//*[@id=\"description\"]")
	public WebElement projectDescInput;
	
	@FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div[1]/div[3]/button[2]/span")
	public WebElement projectConfirmBtn;

}
