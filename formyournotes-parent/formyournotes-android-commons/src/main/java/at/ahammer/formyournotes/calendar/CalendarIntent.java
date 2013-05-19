package at.ahammer.formyournotes.calendar;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import at.ahammer.formyournotes.intent.AbstractIntent;

public class CalendarIntent extends AbstractIntent {

	private String title;
	private String location;
	private String description;
	private boolean allDay = false;
	private Calendar beginDate;
	private Calendar endDate;

	public CalendarIntent(Context context) {
		super(context);
	}

	public String getTitle() {
		return title;
	}

	public CalendarIntent setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public CalendarIntent setLocation(String location) {
		this.location = location;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CalendarIntent setDescription(String description) {
		this.description = description;
		return this;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public CalendarIntent setAllDay(boolean allDay) {
		this.allDay = allDay;
		return this;
	}

	public Calendar getBeginDate() {
		return beginDate;
	}

	public CalendarIntent setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
		return this;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public CalendarIntent setEndDate(Calendar endDate) {
		this.endDate = endDate;
		return this;
	}

	@Override
	public void perform() {
		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setData(CalendarContract.Events.CONTENT_URI);
		intent.putExtra(Events.TITLE, getTitle());
		intent.putExtra(Events.EVENT_LOCATION, getLocation());
		intent.putExtra(Events.DESCRIPTION, getDescription());
		intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, isAllDay());
		if (getBeginDate() != null) {
			intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
					getBeginDate().getTimeInMillis());
		}
		if (getEndDate() != null) {
			intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, getEndDate()
					.getTimeInMillis());
		}
		getContext().startActivity(
				Intent.createChooser(intent, "Start insert event..."));
	}
}
