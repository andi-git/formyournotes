package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.ahammer.formyournotes.beans.GroupBean.Border;
import at.ahammer.formyournotes.beans.GroupBean.Orientation;
import at.ahammer.formyournotes.data.CalendarData;
import at.ahammer.formyournotes.data.CheckBoxData;
import at.ahammer.formyournotes.data.ContactData;
import at.ahammer.formyournotes.data.EditTextData;
import at.ahammer.formyournotes.data.EventData;
import at.ahammer.formyournotes.data.FormData;

public class FormBean {

	private int id = -1;
	private String name = "unknown";
	private FormData addedData = new FormData();
	private boolean deleted = false;
	// work here with concrete classes, otherwise GSON has problems!
	private List<CheckBoxBean> checkBoxBeans = new ArrayList<CheckBoxBean>();
	private List<CheckBoxGroupBean> checkBoxGroupBeans = new ArrayList<CheckBoxGroupBean>();
	private List<ContactBean> contactBeans = new ArrayList<ContactBean>();
	private List<EditTextBean> editTextBeans = new ArrayList<EditTextBean>();
	private List<GroupBean> groupBeans = new ArrayList<GroupBean>();
	private List<CalendarBean> calendarBeans = new ArrayList<CalendarBean>();
	private List<EventBean> eventBeans = new ArrayList<EventBean>();
	private boolean dataChanged = false;

	// TODO cache for ranking - bean

	public FormBean() {
		super();
	}

	public FormBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		// set id of form to all containing data
		for (FormYourNotesBean<?> bean : getAllItems()) {
			bean.getData().setItemId(id);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CheckBoxBean> getCheckBoxBeans() {
		return checkBoxBeans;
	}

	public void addCheckBoxBean(CheckBoxBean checkBoxBean) {
		checkBoxBeans.add(checkBoxBean);
	}

	public List<CheckBoxGroupBean> getCheckBoxGroupBeans() {
		return checkBoxGroupBeans;
	}

	public void addCheckBoxGroupBean(CheckBoxGroupBean checkBoxGroupBean) {
		checkBoxGroupBeans.add(checkBoxGroupBean);
	}

	public List<ContactBean> getContactBeans() {
		return contactBeans;
	}

	public void addContactBean(ContactBean contactBean) {
		contactBeans.add(contactBean);
	}

	public List<GroupBean> getGroupBeans() {
		return groupBeans;
	}

	public void addGroupBean(GroupBean groupBean) {
		groupBeans.add(groupBean);
	}

	public List<EditTextBean> getEditTextBeans() {
		return editTextBeans;
	}

	public void addEditTextBean(EditTextBean editTextBean) {
		editTextBeans.add(editTextBean);
	}

	public List<CalendarBean> getCalendarBeans() {
		return calendarBeans;
	}

	public void addCalendarBean(CalendarBean calendarBean) {
		calendarBeans.add(calendarBean);
	}

	public List<EventBean> getEventBeans() {
		return eventBeans;
	}

	public void addEventBean(EventBean eventBean) {
		eventBeans.add(eventBean);
	}

	private void addCommonData(FormYourNotesBean<?> bean, int id, int rank,
			int parent) {
		bean.setId(id);
		bean.setParent(parent);
		bean.setRank(rank);
	}

	public CheckBoxBean newCheckBoxBean(int id, int rank, int parent,
			String discription) {
		CheckBoxBean checkBoxBean = new CheckBoxBean();
		addCommonData(checkBoxBean, id, rank, parent);
		checkBoxBean.setDiscription(discription);
		addCheckBoxBean(checkBoxBean);
		return checkBoxBean;
	}

	public CheckBoxBean newCheckBoxBean(int id, int rank,
			FormYourNotesBean<?> parent, String discription) {
		return newCheckBoxBean(id, rank, parent.getId(), discription);
	}

	public CheckBoxGroupBean newCheckBoxGroupBean(int id, int rank, int parent,
			String discription) {
		CheckBoxGroupBean checkBoxGroupBean = new CheckBoxGroupBean();
		addCommonData(checkBoxGroupBean, id, rank, parent);
		checkBoxGroupBean.setDiscription(discription);
		addCheckBoxGroupBean(checkBoxGroupBean);
		return checkBoxGroupBean;
	}

	public CheckBoxGroupBean newCheckBoxGroupBean(int id, int rank,
			FormYourNotesBean<?> parent, String discription) {
		return newCheckBoxGroupBean(id, rank, parent.getId(), discription);
	}

	public ContactBean newContactBean(int id, int rank, int parent,
			String discription) {
		ContactBean contactBean = new ContactBean();
		addCommonData(contactBean, id, rank, parent);
		contactBean.setDiscription(discription);
		addContactBean(contactBean);
		return contactBean;
	}

	public ContactBean newContactBean(int id, int rank,
			FormYourNotesBean<?> parent, String discription) {
		return newContactBean(id, rank, parent.getId(), discription);
	}

	public EditTextBean newEditTextBean(int id, int rank, int parent,
			String discription) {
		EditTextBean editTextBean = new EditTextBean();
		addCommonData(editTextBean, id, rank, parent);
		editTextBean.setDiscription(discription);
		addEditTextBean(editTextBean);
		return editTextBean;
	}

	public EditTextBean newEditText(int id, int rank,
			FormYourNotesBean<?> parent, String discription) {
		return newEditTextBean(id, rank, parent.getId(), discription);
	}

	public CalendarBean newCalendarBean(int id, int rank, int parent,
			String discription, CalendarBean.Type type, boolean showInvoke) {
		CalendarBean calendarBean = new CalendarBean();
		addCommonData(calendarBean, id, rank, parent);
		calendarBean.setDiscription(discription);
		calendarBean.setType(type);
		calendarBean.setShowInvoke(showInvoke);
		addCalendarBean(calendarBean);
		return calendarBean;
	}

	public CalendarBean newCalendar(int id, int rank,
			FormYourNotesBean<?> parent, String discription,
			CalendarBean.Type type, boolean showInvoke) {
		return newCalendarBean(id, rank, parent != null ? parent.getId() : -1,
				discription, type, showInvoke);
	}

	public EventBean newEventBean(int id, int rank, int parent,
			String discription, CalendarBean date, CalendarBean time) {
		EventBean eventBean = new EventBean();
		addCommonData(eventBean, id, rank, parent);
		eventBean.setDiscription(discription);
		date.setParent(eventBean);
		eventBean.setDate(date);
		time.setParent(eventBean);
		eventBean.setTime(time);
		addEventBean(eventBean);
		return eventBean;
	}

	public EventBean newEvent(int id, int rank, FormYourNotesBean<?> parent,
			String discription, CalendarBean date, CalendarBean time) {
		return newEventBean(id, rank, parent.getId(), discription, date, time);
	}

	public GroupBean newGroupBean(int id, int rank, int parent, String name,
			Orientation orientation, Border border) {
		GroupBean groupBean = new GroupBean();
		addCommonData(groupBean, id, rank, parent);
		groupBean.setName(name);
		groupBean.setOrientation(orientation);
		groupBean.setBorder(border);
		addGroupBean(groupBean);
		return groupBean;
	}

	public GroupBean newGroupBean(int id, int rank,
			FormYourNotesBean<?> parent, String name, Orientation orientation,
			Border border) {
		return newGroupBean(id, rank, parent.getId(), name, orientation, border);
	}

	public List<FormYourNotesBean<?>> getAllItemsSortedByRank() {
		List<FormYourNotesBean<?>> items = getAllItems();
		sortByRank(items);
		return items;
	}

	private void sortByRank(List<FormYourNotesBean<?>> items) {
		Collections.sort(items, new Comparator<FormYourNotesBean<?>>() {

			@Override
			public int compare(FormYourNotesBean<?> bean1,
					FormYourNotesBean<?> bean2) {
				if (bean1.getRank() > bean2.getRank()) {
					return 1;
				} else if (bean1.getRank() < bean2.getRank()) {
					return -1;
				} else {
					if (bean1.getId() > bean2.getId()) {
						return 1;
					} else if (bean1.getId() < bean2.getId()) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		});
	}

	private List<FormYourNotesBean<?>> getAllItems() {
		List<FormYourNotesBean<?>> items = new ArrayList<FormYourNotesBean<?>>();
		items.addAll(checkBoxBeans);
		items.addAll(checkBoxGroupBeans);
		items.addAll(contactBeans);
		items.addAll(editTextBeans);
		items.addAll(groupBeans);
		items.addAll(calendarBeans);
		items.addAll(eventBeans);
		return items;
	}

	public List<FormYourNotesBean<?>> getAllTopLevelItemsSortedByRank() {
		List<FormYourNotesBean<?>> topLevelItems = new ArrayList<FormYourNotesBean<?>>();
		for (FormYourNotesBean<?> item : getAllItems()) {
			if (item.isTopLevelElement()) {
				topLevelItems.add(item);
			}
		}
		sortByRank(topLevelItems);
		return topLevelItems;
	}

	public List<FormYourNotesBean<?>> getAllChildren(FormYourNotesBean<?> bean) {
		return getAllChildren(bean.getId());
	}

	public List<FormYourNotesBean<?>> getAllChildren(int id) {
		List<FormYourNotesBean<?>> children = new ArrayList<FormYourNotesBean<?>>();
		for (FormYourNotesBean<?> item : getAllItems()) {
			if (item.getParent() == id) {
				children.add(item);
			}
		}
		sortByRank(children);
		return children;
	}

	private void clearData() {
		dataChanged = false;
		addedData = new FormData();
		for (CheckBoxBean checkBoxBean : checkBoxBeans) {
			checkBoxBean.clearData();
		}
		for (CheckBoxGroupBean checkBoxGroupBean : checkBoxGroupBeans) {
			checkBoxGroupBean.clearData();
		}
		for (ContactBean contactBean : contactBeans) {
			contactBean.clearData();
		}
		for (EditTextBean editTextBean : editTextBeans) {
			editTextBean.clearData();
		}
		for (GroupBean groupBean : groupBeans) {
			groupBean.clearData();
		}
		for (CalendarBean calendarBean : calendarBeans) {
			calendarBean.clearData();
		}
		for (EventBean eventBean : eventBeans) {
			eventBean.clearData();
		}
	}

	public FormBean setData(FormData formData) {
		clearData();
		dataChanged = false;
		addedData = formData;
		for (CheckBoxData checkBoxData : formData.getCheckBoxData()) {
			getById(checkBoxData.getItemId(), CheckBoxBean.class).setData(
					checkBoxData);
		}
		for (ContactData contactData : formData.getContactData()) {
			getById(contactData.getItemId(), ContactBean.class).setData(
					contactData);
		}
		for (EditTextData editTextData : formData.getEditTextData()) {
			getById(editTextData.getItemId(), EditTextBean.class).setData(
					editTextData);
		}
		for (CalendarData calendarData : formData.getCalendarData()) {
			getById(calendarData.getItemId(), CalendarBean.class).setData(
					calendarData);
		}
		for (EventData eventData : formData.getEventData()) {
			getById(eventData.getItemId(), EventBean.class).setData(eventData);
		}
		return this;
	}

	public FormData getData() {
		if (addedData != null) {
			addedData.clearCheckBoxData();
			addedData.clearContactData();
			addedData.clearEditTextData();
			addedData.clearCalendarData();
			addedData.clearEventData();
			for (CheckBoxBean checkBoxBean : checkBoxBeans) {
				addedData.add(checkBoxBean.getData());
			}
			for (ContactBean contactBean : contactBeans) {
				addedData.add(contactBean.getData());
			}
			for (EditTextBean editTextBean : editTextBeans) {
				addedData.add(editTextBean.getData());
			}
			for (CalendarBean calendarBean : calendarBeans) {
				addedData.add(calendarBean.getData());
			}
			for (EventBean eventBean : eventBeans) {
				addedData.add(eventBean.getData());
			}
		}
		return addedData;
	}

	public FormYourNotesBean<?> getById(int id) {
		for (FormYourNotesBean<?> currentBean : getAllItems()) {
			if (currentBean.getId() > 0 && currentBean.getId() == id) {
				return currentBean;
			}
		}
		throw new RuntimeException("Can't find item in form with id " + id);
	}

	public <T> T getById(int id, Class<T> clazz) {
		FormYourNotesBean<?> bean = null;
		for (FormYourNotesBean<?> currentBean : getAllItems()) {
			if (currentBean.getId() > 0 && currentBean.getId() == id) {
				bean = currentBean;
				break;
			}
		}
		if (bean == null) {
			throw new RuntimeException("Can't find item in form with id " + id);
		}
		if (clazz == bean.getClass()) {
			@SuppressWarnings("unchecked")
			T result = (T) bean;
			return result;
		} else {
			throw new RuntimeException("Can't cast " + bean.getClass() + " to "
					+ clazz);
		}
	}

	public boolean possibleDataChange(Object oldValue, Object newValue) {
		dataChanged = (oldValue == null && newValue != null)
				|| !oldValue.equals(newValue);
		return dataChanged;
	}

	public boolean possibleDataChange(boolean oldValue, boolean newValue) {
		dataChanged = !oldValue == newValue;
		return dataChanged;
	}

	public boolean hasDataChanged() {
		return dataChanged;
	}

	public void setDataChange(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
