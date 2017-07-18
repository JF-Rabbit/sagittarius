package org.sagittarius.uitest.web.page.dataAnalysis.editProject.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class ComponentPage implements PageUI {

	@FindBy(id = "kmx")
	public WebElement kmxTimeSeriesDataSourc;

	@FindBy(id = "hdfs")
	public WebElement hdfsDataSourc;

	@FindBy(id = "object")
	public WebElement kmxObjectDataSourc;

	@FindBy(id = "script-upload")
	public WebElement script;
}
