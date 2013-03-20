package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.ahammer.formyournotes.data.CheckBoxData;
import at.ahammer.formyournotes.data.ContactData;
import at.ahammer.formyournotes.data.EditTextData;
import at.ahammer.formyournotes.data.FormData;

public class FormBean {

	private int id = -1;
	private String name = "unknown";
	// work here with concrete classes, otherwise GSON has problems!
	private List<CheckBoxBean> checkBoxBeans = new ArrayList<CheckBoxBean>();
	private List<CheckBoxGroupBean> checkBoxGroupBeans = new ArrayList<CheckBoxGroupBean>();
	private List<ContactBean> contactBeans = new ArrayList<ContactBean>();
	private List<EditTextBean> editTextBeans = new ArrayList<EditTextBean>();
	private List<GroupBean> groupBeans = new ArrayList<GroupBean>();

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

	public GroupBean newGroupBean(int id, int rank, int parent, String name) {
		GroupBean groupBean = new GroupBean();
		addCommonData(groupBean, id, rank, parent);
		groupBean.setName(name);
		addGroupBean(groupBean);
		return groupBean;
	}

	public GroupBean newGroupBean(int id, int rank,
			FormYourNotesBean<?> parent, String name) {
		return newGroupBean(id, rank, parent.getId(), name);
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
	}

	public FormBean setData(FormData formData) {
		clearData();
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
		return this;
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
}