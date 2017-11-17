package org.sagittarius.common.person;

import java.util.Random;

/**
 * 和电话相关的方法类
 * 
 * @author jasonzhang 2016年11月3日 下午5:43:42
 *
 */
public class Telphone {
	/** 默认 测试手机号前缀136 */
	public static final String TEST_RANDOM_PHONE_PREFIX = "136";

	/**
	 * 随机生成以PhoneNum为前缀的手机号
	 * 
	 * @param phoneNum
	 *            例如199
	 * @return
	 */
	public static String getRandomPhoneNum(String phoneNum) {
		int randomNum = 11 - phoneNum.length();
		for (int i = 0; i < randomNum; i++) {
			phoneNum += "1234567890".charAt(new Random().nextInt(10));
		}
		return phoneNum;
	}

}
