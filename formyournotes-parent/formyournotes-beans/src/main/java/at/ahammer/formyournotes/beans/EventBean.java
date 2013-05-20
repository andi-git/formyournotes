package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.EventData;

public class EventBean extends FormYourNotesBean<EventData> {

	private String discription;
	private CalendarBean date;
	private CalendarBean time;
	private EventData data = new EventData();

	public EventBean() {
		super();
	}

	public EventBean(String discription, CalendarBean date, CalendarBean time) {
		super();
		this.discription = discription;
		this.date = date;
		this.time = time;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public CalendarBean getDate() {
		return date;
	}

	public void setDate(CalendarBean date) {
		this.date = date;
	}

	public CalendarBean getTime() {
		return time;
	}

	public void setTime(CalendarBean time) {
		this.time = time;
	}

	@Override
	public void setData(EventData data) {
		this.data = data;
		date.setData(data.getDate());
		time.setData(data.getTime());
	}

	@Override
	public EventData getData() {
		return data;
	}

	@Override
	public void clearData() {
		data = new EventData();
	}

	@Override
	public boolean canBeParent() {
		return true;
	}
}