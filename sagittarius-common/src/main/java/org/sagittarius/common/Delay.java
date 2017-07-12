package org.sagittarius.common;

public class Delay {

	public static void sleep(int millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
