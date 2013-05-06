package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.CalendarData;

public class CalendarBean extends FormYourNotesBean<CalendarData> {

	public static enum Type {
		DATE("00.00.0000"), TIME("00:00");

		private final String defaultValue;

		Type(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
	}

	private String discription;
	private CalendarData data = new CalendarData();
	private Type type = Type.DATE;

	public CalendarBean() {
		super();
	}

	public CalendarBean(String discription, Type type) {
		super();
		this.discription = discription;
		this.type = type;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getValue() {
		return data.getValue();
	}

	public String getDefaultValue() {
		return type.getDefaultValue();
	}

	public void setValue(String value) {
		data.setValue(value);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public void setData(CalendarData data) {
		this.data = data;
	}

	@Override
	public CalendarData getData() {
		return data;
	}

	@Override
	public void clearData() {
		data = new CalendarData();
	}

	@Override
	public boolean canBeParent() {
		return false;
	}
}