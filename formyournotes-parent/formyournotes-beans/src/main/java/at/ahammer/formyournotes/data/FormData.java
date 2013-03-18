package at.ahammer.formyournotes.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormData {

	private int id = -1;
	private String name = "unknown";
	private final List<CheckBoxData> checkBoxDataList = new ArrayList<CheckBoxData>();
	private final List<ContactData> contactDataList = new ArrayList<ContactData>();
	private final List<EditTextData> editTextDataList = new ArrayList<EditTextData>();

	public FormData() {
		super();
	}

	public FormData(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public List<CheckBoxData> getCheckBoxData() {
		return Collections.unmodifiableList(checkBoxDataList);
	}

	public List<CheckBoxData> add(CheckBoxData checkBoxData) {
		checkBoxDataList.add(checkBoxData);
		return getCheckBoxData();
	}

	public List<CheckBoxData> remove(CheckBoxData checkBoxData) {
		checkBoxDataList.remove(checkBoxData);
		return getCheckBoxData();
	}

	public List<ContactData> getContactData() {
		return Collections.unmodifiableList(contactDataList);
	}

	public List<ContactData> add(ContactData contactData) {
		contactDataList.add(contactData);
		return getContactData();
	}

	public List<ContactData> remove(ContactData contactData) {
		contactDataList.remove(contactData);
		return getContactData();
	}

	public List<EditTextData> getEditTextData() {
		return Collections.unmodifiableList(editTextDataList);
	}

	public List<EditTextData> add(EditTextData editTextData) {
		editTextDataList.add(editTextData);
		return getEditTextData();
	}

	public List<EditTextData> remove(EditTextData editTextData) {
		editTextDataList.remove(editTextData);
		return getEditTextData();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}