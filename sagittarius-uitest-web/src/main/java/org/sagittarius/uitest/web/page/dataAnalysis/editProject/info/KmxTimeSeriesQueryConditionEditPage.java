package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

import org.openqa.selenium.WebElement;
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
	
	@FindBy(className = "ant-select-selection__placeholder")
	public WebElement idSelect;
	
	@FindBy(className = "ant-select-dropdown-menu-item")
	public WebElement dropDownOne;
	
	@FindBy(xpath = "//input[@placeholder='请输入']") // XXX 有多个
	public WebElement idInput;
	
	@FindBy(tagName = "textarea")
	public WebElement otherInput;
}
