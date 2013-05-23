package at.ahammer.formyournotes.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormData {

	private int formId = -1;
	private int dataId = -1;
	private String name = "unknown";
	private boolean deleted = false;
	private final List<CheckBoxData> checkBoxDataList = new ArrayList<CheckBoxData>();
	private final List<ContactData> contactDataList = new ArrayList<ContactData>();
	private final List<EditTextData> editTextDataList = new ArrayList<EditTextData>();
	private final List<CalendarData> calendarDataList = new ArrayList<CalendarData>();
	private final List<EventData> eventDataList = new ArrayList<EventData>();
	private final List<SelectData> selectDataList = new ArrayList<SelectData>();

	public FormData() {
		super();
	}

	public FormData(int formId, int dataId, String name) {
		super();
		this.formId = formId;
		this.dataId = dataId;
		this.name = name;
	}

	public List<CheckBoxData> getCheckBoxData() {
		return Collections.unmodifiableList(checkBoxDataList);
	}

	public List<CheckBoxData> add(CheckBoxData checkBoxData) {
		if (checkBoxData.isFilled()) {
			checkBoxDataList.add(checkBoxData);
		}
		return getCheckBoxData();
	}

	public List<CheckBoxData> remove(CheckBoxData checkBoxData) {
		checkBoxDataList.remove(checkBoxData);
		return getCheckBoxData();
	}

	public void clearCheckBoxData() {
		checkBoxDataList.clear();
	}

	public List<ContactData> getContactData() {
		return Collections.unmodifiableList(contactDataList);
	}

	public List<ContactData> add(ContactData contactData) {
		if (contactData.isFilled()) {
			contactDataList.add(contactData);
		}
		return getContactData();
	}

	public List<ContactData> remove(ContactData contactData) {
		contactDataList.remove(contactData);
		return getContactData();
	}

	public void clearContactData() {
		contactDataList.clear();
	}

	public List<EditTextData> getEditTextData() {
		return Collections.unmodifiableList(editTextDataList);
	}

	public List<EditTextData> add(EditTextData editTextData) {
		if (editTextData.isFilled()) {
			editTextDataList.add(editTextData);
		}
		return getEditTextData();
	}

	public List<EditTextData> remove(EditTextData editTextData) {
		editTextDataList.remove(editTextData);
		return getEditTextData();
	}

	public void clearEditTextData() {
		editTextDataList.clear();
	}

	public List<CalendarData> getCalendarData() {
		return Collections.unmodifiableList(calendarDataList);
	}

	public List<CalendarData> add(CalendarData calendarData) {
		if (calendarData.isFilled()) {
			calendarDataList.add(calendarData);
		}
		return getCalendarData();
	}

	public List<CalendarData> remove(CalendarData calendarData) {
		calendarDataList.remove(calendarData);
		return getCalendarData();
	}

	public void clearCalendarData() {
		calendarDataList.clear();
	}

	public List<EventData> getEventData() {
		return Collections.unmodifiableList(eventDataList);
	}

	public List<EventData> add(EventData eventData) {
		if (eventData.isFilled()) {
			eventDataList.add(eventData);
		}
		return getEventData();
	}

	public List<EventData> remove(EventData eventData) {
		eventDataList.remove(eventData);
		return getEventData();
	}

	public void clearEventData() {
		eventDataList.clear();
	}

	public List<SelectData> getSelectData() {
		return Collections.unmodifiableList(selectDataList);
	}

	public List<SelectData> add(SelectData selectData) {
		if (selectData.isFilled()) {
			selectDataList.add(selectData);
		}
		return getSelectData();
	}

	public List<SelectData> remove(SelectData selectData) {
		selectDataList.remove(selectData);
		return getSelectData();
	}

	public void clearSelectData() {
		selectDataList.clear();
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "FormData [formId=" + formId + ", dataId=" + dataId + ", name="
				+ name + ", deleted=" + deleted + ", checkBoxDataList="
				+ checkBoxDataList + ", contactDataList=" + contactDataList
				+ ", editTextDataList=" + editTextDataList
				+ ", calendarDataList=" + calendarDataList + ", eventDataList="
				+ eventDataList + ", selectDataList=" + selectDataList + "]";
	}
}