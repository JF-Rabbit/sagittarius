package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.web.page.CommonPage;

public class KmxTimeSeriesQueryConditionEditPage extends CommonPage {
	
	@FindBy(className = "ant-switch-inner")
	public WebElement staticTimeRangeSwitch;
	
	@FindBy(xpath = "//input[@placeholder='起始时间']")
	public WebElement startTimeReadOnly;
	
	@FindBy(xpath = "//input[@placeholder='结束时间']")
	public WebElement endTimeReadOnly;
	
	@FindBy(className = "ant-calendar-input ")
	public WebElement timeInput;
	
	@FindAll(@FindBy(xpath = "//div[text()='请选择']"))
	public List<WebElement> idSelect;
	
	@FindAll(@FindBy(xpath = "//input[@placeholder='请输入']"))
	public List<WebElement> idInput;
	
	@FindBy(tagName = "textarea")
	public WebElement otherInput;
}
