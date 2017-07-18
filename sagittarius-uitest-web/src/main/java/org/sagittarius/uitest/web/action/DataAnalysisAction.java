package org.sagittarius.uitest.web.action;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sagittarius.common.Delay;
import org.sagittarius.uitest.util.PageElementUtil;
import org.sagittarius.uitest.util.web.WebElementUtil;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.ProjectCanvasPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.component.ComponentPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataAnalysisAction {

	private static final Logger logger = LoggerFactory.getLogger(DataAnalysisAction.class);

	@Deprecated
	public void createComponent1(WebDriver driver) throws InterruptedException {
		ProjectCanvasPage projectCanvasPage = new ProjectCanvasPage();
		ComponentPage componentPage = new ComponentPage();
		PageElementUtil.initPages(driver, projectCanvasPage, componentPage);
		int[] coordinate = WebElementUtil.getElementCenter(projectCanvasPage.editBackground);
		logger.info("x:{}, y:{}", coordinate[0], coordinate[1]);
		logger.info("x:{}, y:{}", componentPage.hdfsDataSourc.getLocation().x, componentPage.hdfsDataSourc.getLocation().y);
		Actions action = new Actions(driver);
		// action.dragAndDropBy(projectCanvasPage.hdfsDataSourc, coordinate[0],
		// coordinate[1]).perform();

		action.dragAndDrop(componentPage.hdfsDataSourc, componentPage.hdfsDataSourc).perform();
		Delay.suspend();
		// action.clickAndHold(projectCanvasPage.hdfsDataSourc);
		// action.moveToElement(projectCanvasPage.hdfsDataSourc, coordinate[0],
		// coordinate[1]);
		// action.release();
		// action.perform();

	}

	@Deprecated
	public void clickTargetProject(WebDriver driver) {
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
