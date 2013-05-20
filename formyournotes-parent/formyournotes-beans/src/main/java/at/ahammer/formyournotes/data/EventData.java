package at.ahammer.formyournotes.data;

public class EventData extends FormYourNotesData {

	private CalendarData date;

	private CalendarData time;

	public EventData() {
		super();
	}

	public EventData(CalendarData date, CalendarData time, int formId) {
		super(formId);
		this.date = date;
		this.time = time;
	}

	public CalendarData getDate() {
		return date;
	}

	public void setDate(CalendarData date) {
		this.date = date;
	}

	public CalendarData getTime() {
		return time;
	}

	public void setTime(CalendarData time) {
		this.time = time;
	}

	public void setData(EventData data) {
		super.setData(data);
		this.date.setData(data.getDate());
		this.time.setData(data.getTime());
	}
}
