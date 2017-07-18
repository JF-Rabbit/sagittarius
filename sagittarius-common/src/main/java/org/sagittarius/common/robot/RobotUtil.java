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
	 * @throws AWTException
	 */
	public static void dragToLocation(int startX, int startY, int endX, int endY) throws AWTException {
		Robot robot = new Robot();
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
	
	public static void pressESC() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		Delay.sleep(100);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
	}
	
}
