package org.sagittarius.uitest.web.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.sagittarius.common.Delay;
import org.sagittarius.common.random.RandomUtil;
import org.sagittarius.uitest.WebTest;
import org.sagittarius.uitest.web.action.CreateProjectInfoAction;
import org.sagittarius.uitest.web.action.EditProjectAction;
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
	CreateProjectInfoAction createProjectInfoAction;

	//@Test
	public void login() throws IOException {
		loginAction.login(driver);
//		createProjectInfoAction.clickCreateProject(driver);
//		createProjectInfoAction.inputProjectInfo(driver, projectName, projectDesc);
		Delay.suspend();
	}

	@Resource
	EditProjectAction editProjectAction;

	String projectName = "selenium_script" + "_" + RandomUtil.randomUUID();
	String projectDesc = "selenium_script";

	String hdfsPath = "/project/workspace";

	/**
	 * 创建HDFS+Script
	 * 
	 * @throws @throws
	 *             IOException
	 */
	// @Test
	public void create_HDFS$Script() throws IOException {
		loginAction.login(driver);

		createProjectInfoAction.clickCreateProject(driver);
		createProjectInfoAction.inputProjectInfo(driver, projectName, projectDesc);
		String dataSource = editProjectAction.createComponent(driver, ComponentEnum.HDFS_DATASOURC, RandomUtil.randomUUID(), 0, 0);
		String script = editProjectAction.createComponent(driver, ComponentEnum.SCRIPT, RandomUtil.randomUUID(), 0, 2);
		editProjectAction.linkPoint(driver, 0, 1);

		logger.info("dataSource:{}, script:{}", dataSource, script);

		Map<String, Object> hdfsMap = new HashMap<String, Object>();
		hdfsMap.put(ComponentInfoConstant.HDFS_PATH, hdfsPath);
		editProjectAction.editCompoment(driver, ComponentEnum.HDFS_DATASOURC, dataSource, hdfsMap);

		Map<String, Object> scriptMap = new HashMap<String, Object>();
		scriptMap.put(ComponentInfoConstant.SCRIPT_TYPE, ComponentInfoConstant.ScriptTypeEnum.DATA_EXTRACT);
		editProjectAction.editCompoment(driver, ComponentEnum.SCRIPT, script, scriptMap);
		editProjectAction.clickSaveBtn(driver);
		Delay.suspend();
	}

	String tableName = "fg_7s_1dot5mw_freqcon_vensys_air_cooling_1";
	String[] fieldArray = new String[] {"type", "fid", "tid", "wnac_wspd_instmag_f" }; // 
	String timeStart = "2016-06-18 20:00:00";
	String endStart = "2016-06-18 20:00:00";
	private Map<String, String> getIDInfo() {
		Map<String, String> map = new HashMap<>();
		
		map.put("fid", "111");
		map.put("tid", "222");
		
		return map;
	}
	String other = "other";

	/**
	 * 创建KMX时序+Script
	 * 
	 * @throws @throws
	 *             IOException
	 */
	@Test
	public void create_KMX_T$Script() throws IOException {
		loginAction.login(driver);

		createProjectInfoAction.clickCreateProject(driver);
		createProjectInfoAction.inputProjectInfo(driver, projectName, projectDesc);
		String dataSource = editProjectAction.createComponent(driver, ComponentEnum.KMX_TIMESERIES_DATASOURC, RandomUtil.randomUUID(), 0,
				0);
		String script = editProjectAction.createComponent(driver, ComponentEnum.SCRIPT, RandomUtil.randomUUID(), 0, 2);
		editProjectAction.linkPoint(driver, 0, 1);

		logger.info("dataSource:{}, script:{}", dataSource, script);

		Map<String, Object> kmxTimeseriesMap = new HashMap<String, Object>();
		kmxTimeseriesMap.put(ComponentInfoConstant.FIELD_TABLE_NAME, tableName);
		kmxTimeseriesMap.put(ComponentInfoConstant.FIELD_ARRAY, fieldArray);
		kmxTimeseriesMap.put(ComponentInfoConstant.HAVE_QUERY_CONDION, ComponentInfoConstant.TRUE);
		kmxTimeseriesMap.put(ComponentInfoConstant.QUERY_CONDION_TIME_START, timeStart);
		kmxTimeseriesMap.put(ComponentInfoConstant.QUERY_CONDION_TIME_END, endStart);
		kmxTimeseriesMap.put(ComponentInfoConstant.QUERY_CONDION_ID, getIDInfo());
		kmxTimeseriesMap.put(ComponentInfoConstant.QUERY_CONDION_OTHER, other);
		editProjectAction.editCompoment(driver, ComponentEnum.KMX_TIMESERIES_DATASOURC, dataSource, kmxTimeseriesMap);

		Map<String, Object> scriptMap = new HashMap<String, Object>();
		scriptMap.put(ComponentInfoConstant.SCRIPT_TYPE, ComponentInfoConstant.ScriptTypeEnum.DATA_EXTRACT);
		editProjectAction.editCompoment(driver, ComponentEnum.SCRIPT, script, scriptMap);
		editProjectAction.clickSaveBtn(driver);
		Delay.suspend();
	}
}
