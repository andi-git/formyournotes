package at.ahammer.formyournotes.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public class DateHelper {

	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			"dd.MM.yyyy");

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
}
