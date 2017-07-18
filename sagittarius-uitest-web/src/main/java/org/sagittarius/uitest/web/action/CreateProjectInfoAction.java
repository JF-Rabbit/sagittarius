package org.sagittarius.uitest.web.action;

import org.openqa.selenium.WebDriver;
import org.sagittarius.uitest.util.PageElementUtil;
import org.sagittarius.uitest.web.page.dataAnalysis.CreateProjectInfoPage;
import org.sagittarius.uitest.web.page.dataAnalysis.DataAnalysisPage;
import org.springframework.stereotype.Component;

@Component
public class CreateProjectInfoAction {

	public void clickCreateProject(WebDriver driver) {
		DataAnalysisPage dataAnalysisPage = new DataAnalysisPage();
		PageElementUtil.initPages(driver, dataAnalysisPage);
		dataAnalysisPage.createProject.click();
	}

	public void inputProjectInfo(WebDriver driver, String projectName, String projectDesc) {
		CreateProjectInfoPage createProjectInfoPage = new CreateProjectInfoPage();
		PageElementUtil.initPages(driver, createProjectInfoPage);
		createProjectInfoPage.projectNameInput.sendKeys(projectName);
		createProjectInfoPage.projectDescInput.sendKeys(projectDesc);
		createProjectInfoPage.projectConfirmBtn.click();
	}
}
