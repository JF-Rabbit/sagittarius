package org.sagittarius.uitest.util.mobile;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.sagittarius.common.Delay;
import org.sagittarius.uitest.exception.FindElementTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
@Component
public class ElementLocator {

	private static Logger logger = LoggerFactory.getLogger(ElementLocator.class);

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
	 * @param findType
	 *            ATPFindMobileElementType 枚举
	 * @param findMsg
	 *            定位参数
	 * @return
	 */
	public MobileElement locateElement(AppiumDriver<MobileElement> driver, FindMobileElementEnum findType, String findMsg) {
		switch (findType) {
			case ID:
				return driver.findElementById(findMsg);
			case CLASS:
				return driver.findElementByClassName(findMsg);
			case UIAUTOMATOR:
				return ((AndroidDriver<MobileElement>) driver).findElementByAndroidUIAutomator(findMsg);
			case XPATH:
				return driver.findElementByXPath(findMsg);
			case ACCESSIBILITYID:
				return driver.findElementByAccessibilityId(findMsg);
			case IOSNSPREDICATE:
				return ((IOSDriver<MobileElement>) driver).findElementByIosNsPredicate(findMsg);
			default:
				return null;
		}

	}

	/**
	 * 定位元素列表
	 * 
	 * @param findType
	 *            ATPFindMobileElementType 枚举
	 * @param findMsg
	 *            定位参数
	 * @return
	 */
	public List<MobileElement> locateElements(AppiumDriver<MobileElement> driver, FindMobileElementEnum findType, String findMsg) {
		switch (findType) {
			case ID:
				return driver.findElementsById(findMsg);
			case CLASS:
				return driver.findElementsByClassName(findMsg);
			case UIAUTOMATOR:
				return ((AndroidDriver<MobileElement>) driver).findElementsByAndroidUIAutomator(findMsg);
			case XPATH:
				return driver.findElementsByXPath(findMsg);
			case ACCESSIBILITYID:
				return driver.findElementsByAccessibilityId(findMsg);
			case IOSNSPREDICATE:
				return ((IOSDriver<MobileElement>) driver).findElementsByIosNsPredicate(findMsg);
			default:
				return null;
		}
	}

	private String toStringTimeoutParam(FindMobileElementEnum findType, String findMsg, LoadElementConfirmEnum loadingType, String expect) {
		return "findType:" + findType + " findMsg:" + findMsg + " loadingType:" + loadingType + " expect:" + expect;
	}

	/**
	 * 加载元素(判断timeout)
	 * 
	 * @param findType
	 *            ATPFindMobileElementType 枚举
	 * @param findMsg
	 *            定位参数
	 * @param confirmType
	 *            ATPLoadElementConfirmType 枚举 
	 *            1:isDisplayed
	 *            2:getText().equals(expect)
	 *            3:content-desc: getAttribute("name").equals(String expect)
	 * @param expect
	 *            预期结果 isDisplayed时传null
	 * @return 找到时返回该元素，没找到时抛出异常
	 * @throws FindElementTimeoutException
	 *             超时异常
	 */
	public MobileElement loadingElement(AppiumDriver<MobileElement> driver, FindMobileElementEnum findType, String findMsg,
			LoadElementConfirmEnum confirmType, String expect) throws FindElementTimeoutException {

		logger.info("Loading Element START");
		Boolean result = false;
		MobileElement target = null;

		int time = findTimeout;
		while (true) {

			if (time == 0) {
				logger.error(toStringTimeoutParam(findType, findMsg, confirmType, expect));
				throw new FindElementTimeoutException("-----Find Element Timeout-----");
			}

			try {
				target = locateElement(driver, findType, findMsg);

				switch (confirmType) {
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
