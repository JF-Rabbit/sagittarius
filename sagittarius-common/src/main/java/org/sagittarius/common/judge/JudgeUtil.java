package org.sagittarius.common.judge;

import java.lang.reflect.Field;

import org.sagittarius.common.reflect.ReflectUnit;

public class JudgeUtil {

	public static boolean isNullStr(String str) {
		return str == null ? false : str.equals("");
	}

	public static boolean isNotNullStr(String str) {
		return !isNullStr(str);
	}

	/** 判断实体类中是否还有null的属性 */
	public static boolean isObjHaveNullField(Object obj) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (ReflectUnit.getField(field, obj) == null) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTrue(Object obj) {
		return obj == null ? false : obj.toString().equals("true");
	}

	public static boolean isFalse(Object obj) {
		return obj == null ? false : obj.toString().equals("false");
	}

}
