package org.sagittarius.uitest.util.mobile.ios;

import org.sagittarius.uitest.util.mobile.TouchActionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class IOSUtil {
	
	private static Logger logger = LoggerFactory.getLogger(IOSUtil.class);

	/**
	 * IOS点击visible为false的控件
	 */
	public static void iosClickVisibleFalseElement(MobileElement element, IOSDriver<?> driver) {

		logger.info("visible:{}", element.getAttribute("visible"));

		int x = element.getLocation().x + element.getSize().width / 2;
		int y = element.getLocation().y + element.getSize().height / 2;

		logger.info("x:{}, y:{}", x, y);

		TouchActionUtil.tap(driver, x, y);
	}
}
