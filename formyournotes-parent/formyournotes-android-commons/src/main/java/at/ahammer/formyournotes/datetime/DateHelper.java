package at.ahammer.formyournotes.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public class DateHelper {

	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			"dd.MM.yyyy");

	public static final String DEFAULT_DATE = "00.00.0000";
	
	public static Calendar parseCalendar(String wellFormedCalendar) {
		Calendar date = Calendar.getInstance();
		try {
			date.setTime(SDF.parse(wellFormedCalendar));
		} catch (ParseException e) {
			Log.i(LogTag.FYN.getTag(), "error on parsing calendar: "
					+ wellFormedCalendar);
		}
		return date;
	}
	
	public static String formatCalendar(Calendar calendar) {
		return SDF.format(calendar.getTime());
	}

	public static String formatCalendar(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return formatCalendar(calendar);
	}
}
