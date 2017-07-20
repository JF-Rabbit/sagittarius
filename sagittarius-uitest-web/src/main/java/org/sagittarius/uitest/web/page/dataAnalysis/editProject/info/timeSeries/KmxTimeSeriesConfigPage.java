package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.timeSeries;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.web.page.CommonPage;

public class KmxTimeSeriesConfigPage extends CommonPage {

	@FindBy(className = "ProjectFields__editBtn___3sRVG")
	public WebElement fieldEditBtn;

	@FindBy(xpath = "//span[text()='预 览']")
	public WebElement previewBtn;

	@FindBy(className = "ProjectQueryPanel__editBtn___3G2pn")
	public WebElement queryConditionEditBtn;

	@FindBy(className = "ProjectParallelGroup__editBtn___19eRP")
	public WebElement groupEditBtn;
}
