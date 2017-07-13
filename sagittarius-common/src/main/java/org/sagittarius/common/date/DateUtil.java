package org.sagittarius.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static String YYYY_MM_DD = "yyyy-MM-dd";
	public static String HH_MM_SS = "HH:mm:ss";

	public static String dateFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static Date formatToDate(String dateFormat) throws ParseException {
		return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(dateFormat);
	}

	public static String dateToTimestamp(Date date) {
		return String.valueOf(date.getTime());
	}

	public static String timestampToFormatDate(String timestamp, String format) {
		Date date = new Date(new Long(timestamp));
		return dateFormat(date, format);
	}

	public static Date timestampToDate(String timestamp) {
		return new Date(new Long(timestamp));
	}
}
