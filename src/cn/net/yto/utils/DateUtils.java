package cn.net.yto.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date parseDate(String text) throws ParseException {
        if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("Date text is empty.");
        }
        return DATE_FORMAT.parse(text);
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

}
