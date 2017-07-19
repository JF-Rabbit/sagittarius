package org.sagittarius.common.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.sagittarius.common.Delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RobotUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RobotUtil.class);

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

	public static void setClipboardData(String string) {
		StringSelection ss = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		logger.info("set msg to SystemClipboard:{}", string);
	}

	public static void action_CONTROL_S_CONTROL_V_ENTER() {
		Robot robot = createRobot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Delay.sleep(200);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Delay.sleep(100);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

}
