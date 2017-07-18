package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.util.PageUI;

public class ScriptConfigPage implements PageUI {
	
	@FindBy(xpath = "//div[text()='请选择']")
	public WebElement secletScriptBtn;
	
	@FindBy(xpath = "//ul/li[text()='数据抽取']")
	public WebElement dataExtract;
	@FindBy(xpath = "//ul/li[text()='数据预处理']")
	public WebElement dataPretreatment;
	@FindBy(xpath = "//ul/li[text()='模型训练']")
	public WebElement dataTrain;
	@FindBy(xpath = "//ul/li[text()='模型评分']")
	public WebElement dataGrade;

}
