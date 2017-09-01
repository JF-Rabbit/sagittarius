package org.sagittarius.uitest.util.mobile;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.sagittarius.common.Delay;
import org.sagittarius.uitest.exception.FindElementTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * 经常使用的
 * 		content-desc
 * 			driver.findElementByAccessibilityId("");
 * 		class name
 * 			driver.findElementByClassName("");
 * 		id
 * 			driver.findElementById("");
 * 		Android UIAutomator
 * 			driver.findElementByAndroidUIAutomator("");
 * 
 * web的 
 * 		driver.findElementByCssSelector("");
 * 		driver.findElementByLinkText("");
 * 		driver.findElementByPartialLinkText("");
 * 不建议使用的 
 * 		IOS UIAutomation 开发效率慢，不建议使用
 * 		td1.driver.findElementByIosUIAutomation("");
 * name 1.5之后不再支持了
 * 		driver.findElementByName("");
 * 		class name 1.5之后不再支持了
 * 		driver.findElementByTagName("");
 * 获取元素效率很慢，不建议大量使用
 * 		driver.findElementByXPath("");
 */
/**
 * 用来定位元素的方法类，若没有定位到元素，返回Null
 * 
 * @author jasonzhang 2016年11月2日 下午4:59:52
 *
 */
public class MobileElementUtil {

	private static Logger logger = LoggerFactory.getLogger(MobileElementUtil.class);

	private int findTimeout = 15;

	public int getFindTimeout() {
		return findTimeout;
	}

	public void setFindTimeout(int findTimeout) {
		this.findTimeout = findTimeout;
	}

	/**
	 * 定位一个元素
	 * 
	 * @param by
	 *            MobileByEnum 枚举
	 * @param msg
	 *            定位参数
	 * @return
	 */
	public MobileElement findElement(AppiumDriver<MobileElement> driver, MobileByEnum by, String msg) {
		switch (by) {
		case ID:
			return driver.findElementById(msg);
		case CLASS:
			return driver.findElementByClassName(msg);
		case UIAUTOMATOR:
			return ((AndroidDriver<MobileElement>) driver).findElementByAndroidUIAutomator(msg);
		case XPATH:
			return driver.findElementByXPath(msg);
		case ACCESSIBILITYID:
			return driver.findElementByAccessibilityId(msg);
		case IOSNSPREDICATE:
			return ((IOSDriver<MobileElement>) driver).findElementByIosNsPredicate(msg);
		default:
			return null;
		}

	}

	/**
	 * 定位元素列表
	 * 
	 * @param by
	 *            MobileByEnum 枚举
	 * @param msg
	 *            定位参数
	 * @return
	 */
	public List<MobileElement> findElements(AppiumDriver<MobileElement> driver, MobileByEnum by, String msg) {
		switch (by) {
		case ID:
			return driver.findElementsById(msg);
		case CLASS:
			return driver.findElementsByClassName(msg);
		case UIAUTOMATOR:
			return ((AndroidDriver<MobileElement>) driver).findElementsByAndroidUIAutomator(msg);
		case XPATH:
			return driver.findElementsByXPath(msg);
		case ACCESSIBILITYID:
			return driver.findElementsByAccessibilityId(msg);
		case IOSNSPREDICATE:
			return ((IOSDriver<MobileElement>) driver).findElementsByIosNsPredicate(msg);
		default:
			return null;
		}
	}

	private String toStringTimeoutParam(MobileByEnum by, String msg, MobileConfirmEnum loadingType, String expect) {
		return "by:" + by + " msg:" + msg + " loadingType:" + loadingType + " expect:" + expect;
	}

	/**
	 * 加载元素(判断timeout)
	 * 
	 * @param by
	 *            MobileByEnum 枚举
	 * @param msg
	 *            定位参数
	 * @param type
	 *            MobileConfirmEnum 枚举
	 *            <p>
	 *            1:isDisplayed
	 *            <p>
	 *            2:getText().equals(expect)
	 *            <p>
	 *            3:content-desc: getAttribute("name").equals(String expect)
	 * @param expect
	 *            预期结果 isDisplayed时传null
	 * @return 找到时返回该元素，没找到时抛出异常
	 * @throws FindElementTimeoutException
	 *             超时异常
	 */
	public MobileElement loadingElement(AppiumDriver<MobileElement> driver, MobileByEnum by, String msg, MobileConfirmEnum type,
			String expect) throws FindElementTimeoutException {

		logger.info("Loading Element START");
		Boolean result = false;
		MobileElement target = null;

		int time = findTimeout;
		while (true) {

			if (time == 0) {
				logger.error(toStringTimeoutParam(by, msg, type, expect));
				throw new FindElementTimeoutException("-----Find Element Timeout-----");
			}

			try {
				target = findElement(driver, by, msg);

				switch (type) {
				case IS_DISPLAYED:
					result = target.isDisplayed();
					break;
				case GET_TEXT_EQUALS:
					result = target.getText().equals(expect);
					break;
				case GET_CONTENT_DESC_EQUALS:
					result = target.getAttribute("name").equals(expect);
					break;
				default:
					break;
				}

			} catch (NoSuchElementException e) {
				logger.info("NoSuchElementException");
				result = false;
			} catch (IndexOutOfBoundsException e) {
				logger.info("IndexOutOfBoundsException");
				result = false;
			} catch (Exception e) {
				result = false;
				logger.error("未捕获的异常", e);
			}

			if (result) {
				logger.info("LoadingElement PASS");
				return target;
			} else {
				logger.info("Can't Find Element -- Retry...");
				Delay.sleep(940);
				time--;
			}
		}
	}
}
