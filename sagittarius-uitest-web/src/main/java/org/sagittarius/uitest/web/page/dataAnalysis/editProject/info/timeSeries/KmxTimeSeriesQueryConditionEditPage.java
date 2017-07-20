package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.timeSeries;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.web.page.CommonPage;

public class KmxTimeSeriesQueryConditionEditPage extends CommonPage {
	
	@FindBy(className = "ant-switch-inner")
	public WebElement staticTimeRangeSwitch;
	
	@FindAll(@FindBy(xpath = "//input[@placeholder='请输入']"))
	public List<WebElement> idInput;
	
	@FindBy(tagName = "textarea")
	public WebElement otherInput;
}
