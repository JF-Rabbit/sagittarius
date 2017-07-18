package org.sagittarius.common.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.sagittarius.common.Delay;

public class RobotUtil {

	/**
	 * move to startX|startY, click and hold to endX|endY, release mouse
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	public static void dragToLocation(int startX, int startY, int endX, int endY) {
		Robot robot = createRobot();
		robot.mouseMove(startX, startY);
		Delay.sleep(100);
		robot.mousePress(KeyEvent.BUTTON1_MASK);
		Delay.sleep(100);
		robot.mouseMove(endX, endY);
		Delay.sleep(100);
		robot.mouseWheel(1);
		Delay.sleep(100);
		robot.mouseRelease(KeyEvent.BUTTON1_MASK);
	}

	public static void moveToClick(int x, int y) {
		Robot robot = createRobot();
		robot.mouseMove(x, y);
		Delay.sleep(100);
		robot.mousePress(KeyEvent.BUTTON1_MASK);
		Delay.sleep(100);
		robot.mouseRelease(KeyEvent.BUTTON1_MASK);
	}

	public static void pressESC() {
		Robot robot = createRobot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		Delay.sleep(100);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
	}

	private static Robot createRobot() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return robot;
	}

}
