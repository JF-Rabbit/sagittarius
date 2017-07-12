package org.sagittarius.uitest.util.mobile;

import org.sagittarius.uitest.exception.SwipeLocationException;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public interface SwipeUtil {

	/**
	 * 由下滑到上
	 * 
	 * @param andriodDriver
	 * @param element
	 * @param duration
	 */
	default void swipeBottomToTop(AndroidDriver<?> andriodDriver, MobileElement element, int duration) {
		int startx = (element.getLocation().x + element.getSize().width) / 2;
		int starty = element.getLocation().y + element.getSize().height;
		int endx = startx;
		int endy = element.getLocation().y;
		andriodDriver.swipe(startx, starty, endx, endy, duration);
	}

	/**
	 * 由右滑到左
	 * 
	 * @param andriodDriver
	 * @param element
	 * @param duration
	 */
	default void swipeRightToLeft(AndroidDriver<?> andriodDriver, MobileElement element, int duration) {
		int startx = element.getLocation().x + element.getSize().width;
		int starty = (element.getLocation().y + element.getSize().height) / 2;
		int endx = element.getLocation().x;
		int endy = starty;
		andriodDriver.swipe(startx, starty, endx, endy, duration);
	}

	default void swipeOneElementToAnother(AndroidDriver<?> andriodDriver, MobileElement one, MobileElement another,
			int duration) throws SwipeLocationException {
		if ((one.getLocation().x == 0 && one.getLocation().y == 0)
				|| (another.getLocation().x == 0 && another.getLocation().y == 0)) {
			throw new SwipeLocationException("x, y不能都为0");
		}
		andriodDriver.swipe(one.getLocation().x + one.getSize().width / 2,
				one.getLocation().y + one.getSize().height / 2, another.getLocation().x + another.getSize().width / 2,
				another.getLocation().y + another.getSize().height / 2, duration);
	}
}
