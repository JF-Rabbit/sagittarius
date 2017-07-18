package org.sagittarius.uitest.web.action;

import java.awt.AWTException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sagittarius.common.Delay;
import org.sagittarius.common.robot.RobotUtil;
import org.sagittarius.uitest.util.PageInitUtil;
import org.sagittarius.uitest.util.web.WebElementUtil;
import org.sagittarius.uitest.util.web.js.JsUtil;
import org.sagittarius.uitest.web.page.dataAnalysis.CreateProjectInfoPage;
import org.sagittarius.uitest.web.page.dataAnalysis.DataAnalysisPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.ProjectCanvasPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.component.ComponentEnum;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.component.ComponentPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.ComponentInfoConstant;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.ComponentInfoConstant.ScriptTypeEnum;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.HDFSInfoPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.ScriptInfoPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataAnalysisAction {

	private static final Logger logger = LoggerFactory.getLogger(DataAnalysisAction.class);

	public static final int EXCURSION_UTIL = 100;

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

	/**
	 * 
	 * @param driver
	 * @param componentEnum
	 * @param multipleX
	 *            x轴偏移倍数
	 * @param multipleY
	 *            y轴偏移倍数
	 * @throws AWTException
	 */
	public String createComponent(WebDriver driver, ComponentEnum componentEnum, String projectName, int multipleX, int multipleY)
			throws AWTException {
		Delay.sleep(1000);

		int startX = getX(driver, componentEnum.getElement(driver));
		int startY = getY(driver, componentEnum.getElement(driver));

		ProjectCanvasPage projectCanvasPage = new ProjectCanvasPage();
		PageInitUtil.initPages(driver, projectCanvasPage);
		int[] coordinate = WebElementUtil.getElementCenter(projectCanvasPage.editBackground);
		int endX = coordinate[0] + multipleX * EXCURSION_UTIL;
		int endY = coordinate[1] + multipleY * EXCURSION_UTIL;
		logger.info("startX:{}, startY:{}, endX:{}, endY:{}", startX, startY, endX, endY);
		RobotUtil.dragToLocation(startX, startY, endX, endY);
		Delay.sleep(1000);
		projectCanvasPage.nameInput.clear();
		projectCanvasPage.nameInput.sendKeys(projectName);
		projectCanvasPage.confirmBtn.click();
		return projectName;
	}

	private int getX(WebDriver driver, WebElement element) {
		return element.getLocation().x + element.getSize().width / 2;
	}

	private int getY(WebDriver driver, WebElement element) {
		return JsUtil.getActualX(driver) + element.getLocation().y + element.getSize().height / 2;
	}

	public void linkPoint(WebDriver driver, int indexStart, int indexEnd) throws AWTException {
		Delay.sleep(1000);
		List<WebElement> list = driver.findElements(By.tagName("circle"));
		int startX = getX(driver, list.get(indexStart));
		int startY = getY(driver, list.get(indexStart));
		int endX = getX(driver, list.get(indexEnd));
		int endY = getY(driver, list.get(indexEnd));
		logger.info("startX:{}, startY:{}, endX:{}, endY:{}", startX, startY, endX, endY);
		RobotUtil.dragToLocation(startX, startY, endX, endY);
	}

	public void editCompoment(WebDriver driver, ComponentEnum componentEnum, String componmentName, Map<String, Object> componmentInfo) {
		List<WebElement> list = driver.findElements(By.tagName("tspan"));
		for (WebElement element : list) {
			if (element.getText().endsWith(componmentName)) {
				element.click();
				break;
			}
		}

		switch (componentEnum) {
		case KMX_TIMESERIES_DATASOURC:
			// TODO
			break;
		case HDFS_DATASOURC:
			HDFSInfoPage hdfsInfoPage = new HDFSInfoPage();
			PageInitUtil.initPages(driver, hdfsInfoPage);
			hdfsInfoPage.hdfsPath.clear();
			hdfsInfoPage.hdfsPath.sendKeys(String.valueOf(componmentInfo.get(ComponentInfoConstant.HDFS_PATH)));
			break;
		case KMX_OBJECT_DATASOURC:
			// TODO
			break;
		case SCRIPT:
			ScriptInfoPage scriptInfoPage = new ScriptInfoPage();
			PageInitUtil.initPages(driver, scriptInfoPage);
			scriptInfoPage.secletScriptBtn.click();
			Delay.sleep(500);
			ComponentInfoConstant.ScriptTypeEnum scriptType = (ScriptTypeEnum) componmentInfo.get(ComponentInfoConstant.SCRIPT_TYPE);
			switch (scriptType) {
			case DATA_EXTRACT:
				scriptInfoPage.dataExtract.click();
				break;
			case DATA_PRETREATMENT:
				scriptInfoPage.dataPretreatment.click();
				// TODO
				break;
			case DATA_TRAIN:
				scriptInfoPage.dataTrain.click();
				// TODO
				break;
			case DATA_GRADE:
				scriptInfoPage.dataGrade.click();
				// TODO
				break;
			}
			break;
		}
	}

	public void clickSaveBtn(WebDriver driver) {
		ProjectCanvasPage projectCanvasPage = new ProjectCanvasPage();
		PageInitUtil.initPages(driver, projectCanvasPage);
		projectCanvasPage.saveBtn.click();
	}

	@Deprecated
	public void createComponent1(WebDriver driver) throws InterruptedException {
		ProjectCanvasPage projectCanvasPage = new ProjectCanvasPage();
		ComponentPage componentPage = new ComponentPage();
		PageInitUtil.initPages(driver, projectCanvasPage, componentPage);
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
