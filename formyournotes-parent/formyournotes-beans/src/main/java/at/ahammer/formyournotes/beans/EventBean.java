package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.EventData;

public class EventBean extends FormYourNotesBean<EventData> {

	private String discription;
	private int date;
	private int time;
	private EventData data = new EventData();

	public EventBean() {
		super();
	}

	public EventBean(String discription, int date, int time) {
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

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public void setData(EventData data) {
		this.data = data;
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