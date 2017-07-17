package org.sagittarius.uitest.web.page.dataAnalysis;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class ScriptInfoPage implements PageUI {
	
	@FindBy(xpath = "//*[@id=\"innerContainer\"]/form/div/div[2]/div[5]/div[2]/section/div/div[1]/div/div/div/div")
	public WebElement secletScriptBtn;
	
	@FindBy(xpath = "/html/body/div[4]/div/div/div/ul/li[1]")
	public WebElement dataExtract;
	@FindBy(xpath = "/html/body/div[4]/div/div/div/ul/li[2]")
	public WebElement dataPretreatment;
	@FindBy(xpath = "/html/body/div[4]/div/div/div/ul/li[3]")
	public WebElement dataTrain;
	@FindBy(xpath = "/html/body/div[4]/div/div/div/ul/li[4]")
	public WebElement dataGrade;

}
