package org.sagittarius.common.util;

import java.math.BigDecimal;

public class CompareData {

	public static int compareAmout(String value1, String value2) {

		BigDecimal val1 = new BigDecimal(value1);
		BigDecimal val2 = new BigDecimal(value2);

		if (val1.compareTo(val2) < 0) {
			return -1;
		}
		if (val1.compareTo(val2) == 0) {
			return 0;
		}
		if (val1.compareTo(val2) > 0) {
			return 1;
		}
		return 0;

	}
}
