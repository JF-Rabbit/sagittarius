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
}
