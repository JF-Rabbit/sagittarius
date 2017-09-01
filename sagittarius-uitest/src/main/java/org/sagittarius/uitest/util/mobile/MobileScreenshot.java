package org.sagittarius.uitest.util.mobile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * 用来截取图片的方法类
 * 
 * @author jasonzhang 2016年11月3日 下午4:51:47
 *
 */
public class MobileScreenshot {

	private static Logger logger = LoggerFactory.getLogger(MobileScreenshot.class);

	/** 系统默认时间 */
	public static final String SYSTEMTIME = "SystemTime";
	public static final String FILETYPE_PNG = ".png";

	public static int screenShoot(AppiumDriver<MobileElement> driver, String saveName, String screenshotPath) throws IOException {

		File screen = null;
		File screenFile = null;
		try {
			screen = driver.getScreenshotAs(OutputType.FILE);
			if (SYSTEMTIME.equals(saveName)) {
				long time = System.currentTimeMillis();
				String temp = String.valueOf(time);
				screenFile = new File(screenshotPath + temp + FILETYPE_PNG);
				FileUtils.copyFile(screen, screenFile);
				return 1;
			} else {
				screenFile = new File(screenshotPath + saveName + FILETYPE_PNG);
				logger.info(screenshotPath);
				FileUtils.copyFile(screen, screenFile);
				return 2;
			}
		} catch (FileNotFoundException e) {
			logger.warn("文件名取值异常，可能包含特殊字符", e);
			screenFile = new File(screenshotPath + ("文件名取值异常，可能包含特殊字符" + String.valueOf(System.currentTimeMillis())) + FILETYPE_PNG);
			FileUtils.copyFile(screen, screenFile);
			return 0;
		} catch (Exception e) {
			logger.error("未捕获的异常", e);
			return -1;
		}
	}

	/**
	 * 截取屏幕中当前元素的图像 MonkeyRunner 比较用法 先调用getTargetElementScreenShoot(WebElement,
	 * String)方法，截取目标元素存放在本地 再调用isImageSameAs(WebElement, String, String)方法进行比较
	 * 
	 * @param targetElement
	 *            所要截取的元素
	 * @param name
	 *            存放在本地的名称
	 * @return 目标元素BufferedImage
	 */
	public static BufferedImage getTargetElementScreenShoot(AndroidDriver<MobileElement> driver, MobileElement targetElement, String saveName,
			String screenshotPath) {
		logger.info("截取屏幕中当前元素的图像 START");

		try {
			// 获取屏幕截屏
			BufferedImage screenShot = ImageIO.read(driver.getScreenshotAs(OutputType.FILE));

			BufferedImage myImage = getSubImage(screenShot, targetElement.getLocation().x, targetElement.getLocation().y,
					targetElement.getSize().width, targetElement.getSize().height);

			String message = (targetElement.getLocation().x + " " + targetElement.getLocation().y + " " + targetElement.getSize().width + " "
					+ targetElement.getSize().height);
			logger.info("DEBUG: " + message);
			logger.info("截取屏幕中当前元素的图像 PASS");

			ImageIO.write(myImage, "png", new File(screenshotPath + saveName + FILETYPE_PNG));

			return myImage;
		} catch (Exception e) {
			logger.info("截取屏幕中当前元素的图像 FAIL", e);
			return null;
		}

	}

	/**
	 * 移植MonkeyRunner的sameAs方法，判断两张图片中的元素是否相同
	 * 
	 * @param driver
	 * @param log
	 * @param targetElement
	 *            目标元素，即查看的元素
	 * @param TargetImgName
	 *            已存放在本地的目标元素图片名称
	 * @param ComparedName
	 *            比较时存储的图片名称
	 * @return
	 */
	public static ImageCompareResult isImageSameAs(AndroidDriver<MobileElement> driver, MobileElement targetElement, String TargetImgName,
			String ComparedName, String screenshotPath) {
		logger.info("移植MonkeyRunner的sameAs方法，判断两张图片是否相同 START");
		ImageCompareResult gicr = null;
		try {
			// 获取本地图片元素
			BufferedImage myImage = ImageIO.read(new File(screenshotPath + TargetImgName + FILETYPE_PNG));
			// 获取目标元素
			BufferedImage otherImage = getTargetElementScreenShoot(driver, targetElement, ComparedName, screenshotPath);

			double percent = sameAs(myImage, otherImage, 1);

			gicr = new ImageCompareResult(percent, 1 == percent);

			String message = (targetElement.getLocation().x + " " + targetElement.getLocation().y + " " + targetElement.getSize().width + " "
					+ targetElement.getSize().height);

			// ImageIO.write(myImage, "png", new File(logPath + "sub_" +
			// TargetImgName + FILETYPE_PNGFILETYPE_PNG));
			ImageIO.write(otherImage, "png", new File(screenshotPath + ComparedName + FILETYPE_PNG));

			logger.info("DEBUG: " + message);
			if (gicr.result) {
				logger.info("移植MonkeyRunner的sameAs方法，判断两张图片是否相同 PASS");
			} else {
				logger.info("移植MonkeyRunner的sameAs方法，判断两张图片是否相同 FAIL");
			}

			return gicr;
		} catch (Exception e) {
			logger.info("移植MonkeyRunner的sameAs方法，判断两张图片是否相同 FAIL", e);
			gicr.result = false;
			gicr.percent = 0;
			return gicr;
		}

	}

	/**
	 * 用来保存图片比较结果的返回类
	 * 
	 * @author jasonzhang 2016年11月3日 下午4:59:44
	 *
	 */
	public static class ImageCompareResult {
		public double percent;
		public boolean result;

		/**
		 * @param rate
		 *            比率 1:完全相同 0:完全不同
		 * @param result
		 */
		public ImageCompareResult(double percent, boolean result) {
			super();
			this.percent = percent;
			this.result = result;
		}

	}

	/**
	 * MonkeyRunner图片比较方法
	 * 
	 * @param myImage
	 *            本地要比较的图片
	 * @param otherImage
	 *            截屏图片
	 * @param percent
	 *            相似度百分比，1为完全一致，0为完全不一致
	 * @return
	 */
	private static double sameAs(BufferedImage myImage, BufferedImage otherImage, double percent) {

		if (otherImage.getWidth() != myImage.getWidth()) {
			return 0;
		}
		if (otherImage.getHeight() != myImage.getHeight()) {
			return 0;
		}

		int width = myImage.getWidth();
		int height = myImage.getHeight();

		int numDiffPixels = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
					numDiffPixels++;
				}
			}
		}

		double numberPixels = height * width;
		double diffPercent = numDiffPixels / numberPixels;
		return 1.0 - diffPercent;
	}

	/**
	 * 对BufferedImage对象截取
	 * 
	 * @param image
	 *            BufferedImage对象
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @return
	 */
	private static BufferedImage getSubImage(BufferedImage image, int x, int y, int w, int h) {
		logger.info(x + "|" + y + "|" + w + "|" + h);
		return image.getSubimage(x, y, w, h);
	}
}
