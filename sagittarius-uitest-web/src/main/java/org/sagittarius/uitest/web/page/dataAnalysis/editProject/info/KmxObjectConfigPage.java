package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.web.page.CommonPage;

public class KmxObjectConfigPage extends CommonPage {
	
	@FindBy(xpath = "//div[@class='ProjectDataSource__selected-object___DGx5i']/a[text()='编辑']")
	public WebElement objectTypeEditBtn;
	
	@FindBy(className = "ProjectParallelGroup__editBtn___19eRP")
	public WebElement groupEditBtn;

}
