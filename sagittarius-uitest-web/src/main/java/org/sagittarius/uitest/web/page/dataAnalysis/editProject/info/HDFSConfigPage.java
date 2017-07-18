package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class HDFSConfigPage implements PageUI{
	
	@FindBy(xpath = "//input[@type='text']")
	public WebElement hdfsPath;

}
