package org.sagittarius.common.test;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class TestRobot {

    public static void main(String[] args) throws Exception {
        Thread.sleep(3000);
        Robot robot = new Robot();
        robot.mouseMove(405, 363);
        robot.delay(3000);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
        robot.mouseMove(605, 363);
        robot.delay(3000);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
        robot.mouseMove(605, 463);
        robot.delay(3000);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
        robot.mouseMove(405, 463);
        robot.delay(3000);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        robot.delay(3000);
    }
}
