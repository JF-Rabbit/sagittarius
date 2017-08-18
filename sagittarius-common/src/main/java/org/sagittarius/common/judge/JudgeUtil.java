package org.sagittarius.common.judge;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.sagittarius.common.reflect.ReflectUnit;

public class JudgeUtil {

	public static boolean isNotNullStr(String obj) {
		return (obj != null && !obj.equals(""));
	}

	public static boolean objEqualsStr(Object actual, String expect) {
		if (actual == null) {
			return false;
		}
		if (String.valueOf(actual).equals(expect)) {
			return true;
		}
		return false;
	}

	public static int compareAmout(String value1, String value2) {

		BigDecimal val1 = new BigDecimal(value1);
		BigDecimal val2 = new BigDecimal(value2);

		if (val1.compareTo(val2) < 0) {
			return -1;
		} else if (val1.compareTo(val2) == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public static boolean isObjHaveNullField(Object obj) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (ReflectUnit.getField(field, obj) == null) {
				return true;
			}
		}
		return false;
	}

}
