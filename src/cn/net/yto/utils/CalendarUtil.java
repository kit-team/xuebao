package cn.net.yto.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {
	
	public static final int ONE_SECOND = 1000;
	public static final int ONE_MINUTE = 60 * ONE_SECOND;
	public static final int ONE_HOUR = 60 * ONE_MINUTE;
	public static final int ONE_DAY = 24 * ONE_HOUR;

	/**
	 * Roll some days at the Calendar
	 * @param c original calendar
	 * @param days
	 * @return new calendar
	 */
	public static Calendar rollSomeDays(Calendar c, int days) {
		Date now = c.getTime();
		Calendar newCalendar = Calendar.getInstance(Locale.CHINA);
		newCalendar.setTime(new Date(now.getTime() + days * ONE_DAY));
		return newCalendar;
	}
}
