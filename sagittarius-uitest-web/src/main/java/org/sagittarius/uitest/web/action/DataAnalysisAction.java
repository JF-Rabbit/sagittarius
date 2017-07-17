package org.sagittarius.uitest.web.action;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sagittarius.common.Delay;
import org.sagittarius.uitest.util.PageInitUtil;
import org.sagittarius.uitest.util.web.WebElementUtil;
import org.sagittarius.uitest.web.page.dataAnalysis.CreateProjectInfoPage;
import org.sagittarius.uitest.web.page.dataAnalysis.CreateProjectPage;
import org.sagittarius.uitest.web.page.dataAnalysis.DataAnalysisPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataAnalysisAction {

	private static final Logger logger = LoggerFactory.getLogger(DataAnalysisAction.class);

	public void clickCreateProject(WebDriver driver) {
		DataAnalysisPage dataAnalysisPage = new DataAnalysisPage();
		PageInitUtil.initPages(driver, dataAnalysisPage);
		dataAnalysisPage.createProject.click();
	}

	public void inputProjectInfo(WebDriver driver, String projectName, String projectDesc) {
		CreateProjectInfoPage createProjectInfoPage = new CreateProjectInfoPage();
		PageInitUtil.initPages(driver, createProjectInfoPage);
		createProjectInfoPage.projectNameInput.sendKeys(projectName);
		createProjectInfoPage.projectDescInput.sendKeys(projectDesc);
		createProjectInfoPage.projectConfirmBtn.click();
	}

	public void createComponent(WebDriver driver) throws InterruptedException {
		CreateProjectPage createProjectPage = new CreateProjectPage();
		PageInitUtil.initPages(driver, createProjectPage);
		int[] coordinate = WebElementUtil.getElementCenter(createProjectPage.editBackground);
		logger.info("x:{}, y:{}", coordinate[0], coordinate[1]);
		logger.info("x:{}, y:{}", createProjectPage.hdfsDataSourc.getLocation().x, createProjectPage.hdfsDataSourc.getLocation().y);
		Actions action = new Actions(driver);
		//action.dragAndDropBy(createProjectPage.hdfsDataSourc, coordinate[0], coordinate[1]).perform();
		
		action.dragAndDrop(createProjectPage.hdfsDataSourc, createProjectPage.hdfsDataSourc).perform();
		Delay.suspend();
//		action.clickAndHold(createProjectPage.hdfsDataSourc);
//		action.moveToElement(createProjectPage.hdfsDataSourc, coordinate[0], coordinate[1]);
//		action.release();
//		action.perform();
		
	}
	
	public void clickTargetProject(WebDriver driver){
		driver.findElement(By.xpath("//*[@id=\"innerContainer\"]/div/div[3]/div[2]/div[2]/div[2]/a")).click();
		
		List<WebElement> conPoint = driver.findElements(By.tagName("circle"));
		System.out.println(conPoint.size());
		WebElement start = conPoint.get(0);
		WebElement end = conPoint.get(1);
		logger.info("start:{}, end:{}", start, end);
		Actions action = new Actions(driver);
		action.dragAndDrop(start, end);
		Delay.suspend();
		action.perform();
	}

}
