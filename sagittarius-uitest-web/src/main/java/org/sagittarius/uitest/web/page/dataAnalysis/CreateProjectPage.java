package org.sagittarius.uitest.web.page.dataAnalysis;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class CreateProjectPage implements PageUI {
	
	@FindBy(xpath = "//*[@id=\"kmx\"]/div/span")
	public WebElement kmxTimeSeriesDataSourc;
	
	@FindBy(xpath = "//*[@id=\"hdfs\"]/div/span")
	public WebElement hdfsDataSourc;
	
	@FindBy(xpath = "//*[@id=\"object\"]/div/span")
	public WebElement kmxObjectDataSourc;
	
	@FindBy(xpath = "//*[@id=\"script-upload\"]/div/span")
	public WebElement script;
	
	@FindBy(xpath = "//*[@id=\"v-2\"]")
	public WebElement editBackground;
	
	@FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[1]/div[2]/div/div/div[2]/input")
	public WebElement nameInput;
	
	@FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[1]/div[3]/button[2]/span")
	public WebElement confirmBtn;
	
	@FindBy(xpath = "//*[@id=\"innerContainer\"]/form/div/div[1]/span[3]/i")
	public WebElement saveBtn;
}
