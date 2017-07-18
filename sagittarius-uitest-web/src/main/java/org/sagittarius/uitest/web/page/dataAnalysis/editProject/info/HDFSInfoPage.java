package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class HDFSInfoPage implements PageUI{
	
	@FindBy(xpath = "//*[@id=\"innerContainer\"]/form/div/div[2]/div[5]/div[2]/section/div[2]/input")
	public WebElement hdfsPath;

}
