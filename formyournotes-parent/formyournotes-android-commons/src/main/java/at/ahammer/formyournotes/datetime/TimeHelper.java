package at.ahammer.formyournotes.datetime;

import java.util.Calendar;

import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public class TimeHelper {

	public static final String DEFAULT_TIME = "00:00";
	
	public static class Time {

		boolean unset = true;
		int hour;
		int minute;

		public Time() {
			// nothing
		}

		public Time(Calendar calendar) {
			setHour(calendar.get(Calendar.HOUR_OF_DAY));
			setMinute(calendar.get(Calendar.MINUTE));
		}

		public Time(int hour, int minute) {
			setHour(hour);
			setMinute(minute);
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.unset = false;
			this.hour = hour;
		}

		public int getMinute() {
			return minute;
		}

		public void setMinute(int minute) {
			this.unset = false;
			this.minute = minute;
		}

		public boolean isUnset() {
			return this.unset;
		}
	}

	public static Time parseTime(String wellFormedTime) {
		Time time = new Time();
		if (wellFormedTime != null) {
			String[] splitted = wellFormedTime.split(":");
			if (splitted.length == 2) {
				try {
					time.setHour(Integer.parseInt(splitted[0]));
					time.setMinute(Integer.parseInt(splitted[1]));
				} catch (Exception e) {
					Log.i(LogTag.FYN.getTag(), "unable to parse time " + wellFormedTime + " -> not an int");
				}
			} else {
				Log.i(LogTag.FYN.getTag(), "unable to parse time " + wellFormedTime + " -> no ':'");
			}
		} else {
			Log.i(LogTag.FYN.getTag(), "unable to parse time -> null");
		}
		return time;
	}

	public static String formatTime(int hour, int minute) {
		StringBuilder time = new StringBuilder();
		if (hour < 10) {
			time.append("0");
		}
		time.append(hour);
		time.append(":");
		if (minute < 10) {
			time.append("0");
		}
		time.append(minute);
		return time.toString();
	}

	public static Time currentTime() {
		return new Time(Calendar.getInstance());
	}
}
