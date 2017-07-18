package org.sagittarius.uitest.web.page.dataAnalysis;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.web.page.CommonPage;

public class DataAnalysisPage extends CommonPage{
	
	//@FindBy(xpath = "//*[@id=\"innerContainer\"]/div/div[3]/div[2]/div[2]/div[1]/i")
	//@FindBy(xpath = "//*[@id=\"innerContainer\"]/div/div[3]/div[1]/div[2]/div[1]/i")
	@FindBy(className = "ProjectsPanel__create___2KeDL")
	public WebElement createProject;

}
