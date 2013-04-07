package at.ahammer.formyournotes.beans;

import java.util.Calendar;
import java.util.TimeZone;

public class LastUpdate {

	public static final TimeZone TIME_ZONE = TimeZone
			.getTimeZone("Europe/Berlin");

	private long lastUpdate;

	public LastUpdate() {
		// nothing
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Calendar getAsCalendar() {
		Calendar calendar = Calendar.getInstance(TIME_ZONE);
		calendar.setTimeInMillis(lastUpdate);
		return calendar;
	}

	public void setLastUpdate(Calendar calendar) {
		this.lastUpdate = calendar.getTimeInMillis();
	}

	public static Calendar getDefaultCalendar() {
		return Calendar.getInstance(TIME_ZONE);
	}
}
