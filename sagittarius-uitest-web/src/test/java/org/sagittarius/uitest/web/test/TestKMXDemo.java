package org.sagittarius.uitest.web.test;

import java.awt.AWTException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.sagittarius.common.Delay;
import org.sagittarius.common.random.RandomUtil;
import org.sagittarius.uitest.WebTest;
import org.sagittarius.uitest.web.action.DataAnalysisAction;
import org.sagittarius.uitest.web.action.LoginAction;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.component.ComponentEnum;
import org.sagittarius.uitest.web.page.dataAnalysis.editProject.info.ComponentInfoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestKMXDemo extends WebTest {

	private static final Logger logger = LoggerFactory.getLogger(TestKMXDemo.class);

	@Resource
	LoginAction loginAction;

	String filePath = "D:\\project\\selenium\\chromedriver\\v2.9\\1.txt";
	
	// @Test
	public void test01() throws IOException {

		loginAction.login(driver);
		loginAction.loadTask(driver);
		loginAction.inputTaskName(driver, "任务6");

		loginAction.sendFile(driver, filePath);

		// loginAction.clickLoadBtn(driver);
		// TODO 点击上传后加延迟判断，等待上传完成
		Delay.suspend();
	}

	@Resource
	DataAnalysisAction dataAnalysisAction;

	String projectName = "selenium_input" + "_" + RandomUtil.randomUUID();
	String projectDesc = "selenium_input";

	@Test
	public void test02() throws AWTException, IOException {
		loginAction.login(driver);

		dataAnalysisAction.clickCreateProject(driver);
		dataAnalysisAction.inputProjectInfo(driver, projectName, projectDesc);
		String dataSource = dataAnalysisAction.createComponent(driver, ComponentEnum.HDFS_DATASOURC, RandomUtil.randomUUID(), 0, 0);
		String script = dataAnalysisAction.createComponent(driver, ComponentEnum.SCRIPT, RandomUtil.randomUUID(), 0, 2);
		dataAnalysisAction.linkPoint(driver, 0, 1);
		
		logger.info("dataSource:{}, script:{}", dataSource, script);
		
		Map<String, Object> hdfsMap = new HashMap<String, Object>();
		hdfsMap.put(ComponentInfoConstant.HDFS_PATH, "/project/workspace");
		dataAnalysisAction.editCompoment(driver, ComponentEnum.HDFS_DATASOURC, dataSource, hdfsMap);
		
		Map<String, Object> scriptMap = new HashMap<String, Object>();
		scriptMap.put(ComponentInfoConstant.SCRIPT_TYPE, ComponentInfoConstant.ScriptTypeEnum.DATA_EXTRACT);
		dataAnalysisAction.editCompoment(driver, ComponentEnum.SCRIPT, script, scriptMap);
		dataAnalysisAction.clickSaveBtn(driver);
		Delay.suspend();
	}
}
