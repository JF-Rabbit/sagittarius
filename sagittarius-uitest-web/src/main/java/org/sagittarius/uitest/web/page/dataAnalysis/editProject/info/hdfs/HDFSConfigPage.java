package org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.hdfs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.sagittarius.uitest.web.page.CommonPage;

public class HDFSConfigPage extends CommonPage{
	
	@FindBy(xpath = "//input[@type='text']")
	public WebElement hdfsPath;

}
