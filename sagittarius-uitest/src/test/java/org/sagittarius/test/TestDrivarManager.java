package org.sagittarius.test;

import org.sagittarius.common.Delay;
import org.sagittarius.uitest.WebTest;
import org.sagittarius.uitest.util.web.WebByEnum;
import org.sagittarius.uitest.util.web.WebElementUtil;
import org.testng.annotations.Test;

public class TestDrivarManager extends WebTest{
	
	@Test
	public void doTest1() {
		driver.get("http://www.baidu.com");
		Delay.sleep(3000);
	}

	@Test
	public void testWebDrivrWait() {
		driver.get("http://www.baidu.com");
		WebElementUtil.findElementByWait(driver, 5, WebByEnum.ID, "su");
	}

}
