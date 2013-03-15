package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.EditTextData;

public class EditTextBean extends FormYourNotesBean<EditTextData> {

	private String discription;
	private String value;

	public EditTextBean() {
		super();
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void addData(EditTextData data) {
		value = data.getValue();
	}

	@Override
	public EditTextData getData() {
		EditTextData data = new EditTextData();
		data.setValue(value);
		return data;
	}

	@Override
	public boolean canBeParent() {
		return false;
	}
}