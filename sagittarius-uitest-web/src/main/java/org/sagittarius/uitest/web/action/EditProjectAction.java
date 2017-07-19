package org.sagittarius.uitest.web.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.sagittarius.common.Delay;
import org.sagittarius.common.judge.JudgeUtil;
import org.sagittarius.common.robot.RobotUtil;
import org.sagittarius.uitest.util.PageElementUtil;
import org.sagittarius.uitest.util.web.WebElementUtil;
import org.sagittarius.uitest.util.web.js.JsUtil;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.ProjectCanvasPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.component.ComponentEnum;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.ComponentInfoConstant;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.ComponentInfoConstant.ScriptTypeEnum;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.HDFSConfigPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.KmxTimeSeriesConfigPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.KmxTimeSeriesQueryConditionEditPage;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.ScriptConfigPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EditProjectAction {

	private static final Logger logger = LoggerFactory.getLogger(EditProjectAction.class);

	public static final int EXCURSION_UTIL = 100;

	/**
	 * 
	 * @param driver
	 * @param componentEnum
	 * @param multipleX
	 *            x轴偏移倍数
	 * @param multipleY
	 *            y轴偏移倍数 
	 */
	public String createComponent(WebDriver driver, ComponentEnum componentEnum, String projectName, int multipleX, int multipleY) {
		Delay.sleep(1000);

		int startX = getX(driver, componentEnum.getElement(driver));
		int startY = getY(driver, componentEnum.getElement(driver));

		ProjectCanvasPage projectCanvasPage = new ProjectCanvasPage();
		PageElementUtil.initPages(driver, projectCanvasPage);
		int[] coordinate = WebElementUtil.getElementCenter(projectCanvasPage.editBackground);
		int endX = coordinate[0] + multipleX * EXCURSION_UTIL;
		int endY = coordinate[1] + multipleY * EXCURSION_UTIL;
		logger.info("startX:{}, startY:{}, endX:{}, endY:{}", startX, startY, endX, endY);
		RobotUtil.dragToLocation(startX, startY, endX, endY);
		Delay.sleep(1000);
		PageElementUtil.clearAndSendKey(projectCanvasPage.nameInput, projectName);
		projectCanvasPage.confirmBtn.click();
		return projectName;
	}

	public void linkPoint(WebDriver driver, int indexStart, int indexEnd) {
		Delay.sleep(1000);
		List<WebElement> list = driver.findElements(By.tagName("circle"));
		int startX = getX(driver, list.get(indexStart));
		int startY = getY(driver, list.get(indexStart));
		int endX = getX(driver, list.get(indexEnd));
		int endY = getY(driver, list.get(indexEnd));
		logger.info("startX:{}, startY:{}, endX:{}, endY:{}", startX, startY, endX, endY);
		RobotUtil.dragToLocation(startX, startY, endX, endY);
	}

	private int getX(WebDriver driver, WebElement element) {
		return element.getLocation().x + element.getSize().width / 2;
	}

	private int getY(WebDriver driver, WebElement element) {
		return JsUtil.getActualY(driver) + element.getLocation().y + element.getSize().height / 2;
	}

	public void editCompoment(WebDriver driver, ComponentEnum componentEnum, String componmentName, Map<String, Object> componmentInfo) {
		List<WebElement> list = driver.findElements(By.tagName("tspan"));
		for (WebElement element : list) {
			if (element.getText().endsWith(componmentName)) {
				element.click();
				Delay.sleep(1000);
				break;
			}
		}

		switch (componentEnum) {
		case KMX_TIMESERIES_DATASOURC:
			KmxTimeSeriesConfigPage kmxTimeSeriesConfigPage = new KmxTimeSeriesConfigPage();
			KmxTimeSeriesQueryConditionEditPage kmxTimeSeriesQueryConditionEditPage = new KmxTimeSeriesQueryConditionEditPage();
			PageElementUtil.initPages(driver, kmxTimeSeriesConfigPage, kmxTimeSeriesQueryConditionEditPage);

			editKmxTimeSeriesField(driver, componmentInfo, kmxTimeSeriesConfigPage);

			// kmxTimeSeriesConfigPage.previewBtn.click();
			// TODO 预览按钮

			editKmxTimeSeriesQueryCondition(driver, componmentInfo, kmxTimeSeriesConfigPage, kmxTimeSeriesQueryConditionEditPage);

			editGroup(driver, componmentInfo, kmxTimeSeriesConfigPage);
			
			break;
		case HDFS_DATASOURC:
			HDFSConfigPage hdfsConfigPage = new HDFSConfigPage();
			PageElementUtil.initPages(driver, hdfsConfigPage);
			
			editHDFSPath(componmentInfo, hdfsConfigPage);
			
			break;
		case KMX_OBJECT_DATASOURC:
			// TODO 对象数据源
			break;
		case SCRIPT:
			ScriptConfigPage scriptConfigPage = new ScriptConfigPage();
			PageElementUtil.initPages(driver, scriptConfigPage);
			scriptConfigPage.secletScriptBtn.click();
			Delay.sleep(500);
			ComponentInfoConstant.ScriptTypeEnum scriptType = (ScriptTypeEnum) componmentInfo.get(ComponentInfoConstant.SCRIPT_TYPE);
			
			selectScriptType(scriptConfigPage, scriptType);
			
			break;
		}
	}

	private void editKmxTimeSeriesField(WebDriver driver, Map<String, Object> componmentInfo,
			KmxTimeSeriesConfigPage kmxTimeSeriesConfigPage) {
		kmxTimeSeriesConfigPage.fieldEditBtn.click();
		Delay.sleep(500);
		WebElement checkbox = driver.findElement(By.xpath("//div[text()='" + componmentInfo.get(ComponentInfoConstant.FIELD_TABLE_NAME)
				+ "']/../../../preceding-sibling::span[@class='ant-tree-checkbox']"));
		checkbox.click();
		Delay.sleep(500);
		WebElement field = null;
		String[] fieldArray = (String[]) componmentInfo.get(ComponentInfoConstant.FIELD_ARRAY);
		for (String fieldName : fieldArray) {
			try {
				field = driver.findElement(By.xpath("//span[@title='" + fieldName + "']/preceding-sibling::label/span/input"));
				//field = WebElementUtil.findElementByWait(driver, 10, FindWebElementEnum.X_PATH, "//span[@title='" + fieldName + "']/preceding-sibling::label/span/input");
			} catch (WebDriverException e) {
				List<WebElement> eMenus = driver.findElements(By.xpath("//span[@ref='eMenu']"));
				 eMenus.get(3).click();
				//WebElementUtil.clickDisableElement(driver, eMenus.get(3));
				driver.findElement(By.xpath("//input[@placeholder='字段查询']")).sendKeys(fieldName);
				field = driver.findElement(By.xpath("//span[@title='" + fieldName + "']/preceding-sibling::label/span/input"));
			}
			field.click();
		}
		Delay.sleep(500);
		WebElementUtil.clickOneDisplayedElementofList(kmxTimeSeriesConfigPage.allConfirmBtn);
		Delay.sleep(500);
	}

	@SuppressWarnings("unchecked")
	private void editKmxTimeSeriesQueryCondition(WebDriver driver, Map<String, Object> componmentInfo,
			KmxTimeSeriesConfigPage kmxTimeSeriesConfigPage, KmxTimeSeriesQueryConditionEditPage kmxTimeSeriesQueryConditionEditPage) {
		if (String.valueOf(componmentInfo.get(ComponentInfoConstant.HAVE_QUERY_CONDION)).equals(ComponentInfoConstant.TRUE)) {

			kmxTimeSeriesConfigPage.queryConditionEditBtn.click();
			Delay.sleep(500);

			if (String.valueOf(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_IS_STATIC_TIMEQUERY_CONDION))
					.equals(ComponentInfoConstant.TRUE)) {
				kmxTimeSeriesQueryConditionEditPage.staticTimeRangeSwitch.click();
				Delay.sleep(500);
				// TODO 固定时间范围 
			}

			if (JudgeUtil.isNotNullStr(String.valueOf(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_TIME_START)))) {
				kmxTimeSeriesQueryConditionEditPage.startTimeReadOnly.click();
				Delay.sleep(500);
				PageElementUtil.clearAndSendKey(kmxTimeSeriesQueryConditionEditPage.timeInput,
						String.valueOf(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_TIME_START)));
				Delay.sleep(500);
			}

			if (JudgeUtil.isNotNullStr(String.valueOf(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_TIME_END)))) {
				kmxTimeSeriesQueryConditionEditPage.endTimeReadOnly.click();
				Delay.sleep(500);
				PageElementUtil.clearAndSendKey(kmxTimeSeriesQueryConditionEditPage.timeInput,
						String.valueOf(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_TIME_END)));
				Delay.sleep(500);
			}

			if ((componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_ID) != null) && ((Map<String, String>)(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_ID))).size() != 0) {
				Map<String, String> map = (Map<String, String>)(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_ID));
				
				int index = 0;
				Set<String> keys = map.keySet();
				
				for(String key :keys){
					kmxTimeSeriesQueryConditionEditPage.multiSelectDiv.get(index).click();
					Delay.sleep(500);

					WebElementUtil.clickOneDisplayedElementofList(driver.findElements(By.xpath("//li[contains(text(), '" + key + "')]")));
					Delay.sleep(500);
					
					kmxTimeSeriesQueryConditionEditPage.idInput.get(index).sendKeys(map.get(key));
					Delay.sleep(500);
					index++;
				}
			}

			if (JudgeUtil.isNotNullStr(String.valueOf(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_OTHER)))) {
				kmxTimeSeriesQueryConditionEditPage.otherInput
						.sendKeys(String.valueOf(componmentInfo.get(ComponentInfoConstant.QUERY_CONDION_OTHER)));
			}

			WebElementUtil.clickOneDisplayedElementofList(kmxTimeSeriesQueryConditionEditPage.allConfirmBtn);
			Delay.sleep(500);
		}
	}
	
	private void editGroup(WebDriver driver, Map<String, Object> componmentInfo, KmxTimeSeriesConfigPage kmxTimeSeriesConfigPage) {
		if (JudgeUtil.isNotNullStr(ComponentInfoConstant.GROUP_VALUE)) {
			kmxTimeSeriesConfigPage.groupEditBtn.click();
			Delay.sleep(500);
			WebElementUtil.clickOneDisplayedElementofList(driver.findElements(By.className("ant-select-selection__rendered")));
			Delay.sleep(500);
			driver.findElement(By.xpath("//li[text()='" + String.valueOf(componmentInfo.get(ComponentInfoConstant.GROUP_VALUE)) + "']")).click();
			WebElementUtil.clickOneDisplayedElementofList(kmxTimeSeriesConfigPage.allConfirmBtn);
			Delay.sleep(500);
		}
	}

	private void editHDFSPath(Map<String, Object> componmentInfo, HDFSConfigPage hdfsConfigPage) {
		PageElementUtil.clearAndSendKey(hdfsConfigPage.hdfsPath, String.valueOf(componmentInfo.get(ComponentInfoConstant.HDFS_PATH)));
	}

	private void selectScriptType(ScriptConfigPage scriptConfigPage, ComponentInfoConstant.ScriptTypeEnum scriptType) {
		switch (scriptType) {
		case DATA_EXTRACT:
			scriptConfigPage.dataExtract.click();
			break;
		case DATA_PRETREATMENT:
			scriptConfigPage.dataPretreatment.click();
			// TODO 脚本预处理
			break;
		case DATA_TRAIN:
			scriptConfigPage.dataTrain.click();
			// TODO 模型训练
			break;
		case DATA_GRADE:
			scriptConfigPage.dataGrade.click();
			// TODO 模型评分
			break;
		}
	}

	public void clickSaveBtn(WebDriver driver) {
		ProjectCanvasPage projectCanvasPage = new ProjectCanvasPage();
		PageElementUtil.initPages(driver, projectCanvasPage);
		projectCanvasPage.saveBtn.click();
	}
}
