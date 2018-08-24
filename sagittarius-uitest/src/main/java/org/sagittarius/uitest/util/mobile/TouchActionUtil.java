package org.sagittarius.uitest.util.mobile;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class TouchActionUtil {
    public static void swipe(AppiumDriver<?> driver, int startx, int starty, int endx, int endy, int duration) {
        if (driver instanceof AndroidDriver) {
            new TouchAction(driver)
                    .press(PointOption.point(startx, starty))
                    .waitAction(WaitOptions.waitOptions(changeMillisToDuration(duration)))
                    .moveTo(PointOption.point(endx, endy))
                    .release()
                    .perform();
        }
        if (driver instanceof IOSDriver) {
            int xOffset = endx - startx;
            int yOffset = endy - starty;
            new TouchAction(driver)
                    .press(PointOption.point(startx, starty))
                    .waitAction(WaitOptions.waitOptions(changeMillisToDuration(duration)))
                    .moveTo(PointOption.point(xOffset, yOffset))
                    .release()
                    .perform();
        }
    }

    public static void tap(AppiumDriver<?> driver, int x, int y) {
        TouchAction tap = new TouchAction(driver);
        tap.press(PointOption.point(x, y)).release().perform();
    }

    /**
     * will support on java client BETA-7 or later
     *
     * @param duration
     * @return
     */
    private static Duration changeMillisToDuration(int duration) {
        return Duration.ofMillis(duration);
    }
}
