package org.sagittarius.common.env;

import org.sagittarius.common.properties.SystemPropertyUtil;

public class OprationSystemUtil {

	private static String osName = SystemPropertyUtil.SystemPropertyEnum.OS_NAME.getProperty();

	public static OprationSystemEnum getCurrentSystem() {
		if (lowerCaseContains(osName, OprationSystemEnum.WINDOWS.toString())) {
			return OprationSystemEnum.WINDOWS;
		}
		if (lowerCaseContains(osName, OprationSystemEnum.MAC.toString())) {
			return OprationSystemEnum.MAC;
		}
		if (lowerCaseContains(osName, OprationSystemEnum.LINUX.toString())) {
			return OprationSystemEnum.LINUX;
		}
		return null;
	}

	private static boolean lowerCaseContains(String owner, String one) {
		return owner.toLowerCase().contains(one.toLowerCase());
	}

}
