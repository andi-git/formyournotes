package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.EditTextData;

public class EditTextBean extends FormYourNotesBean<EditTextData> {

	private String discription;
	private EditTextData data = new EditTextData();
	
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
		return data.getValue();
	}

	public void setValue(String value) {
		data.setValue(value);
	}

	@Override
	public void setData(EditTextData data) {
		this.data = data;
	}

	@Override
	public EditTextData getData() {
		return data;
	}

	@Override
	public void clearData() {
		data = new EditTextData();
	}
	
	@Override
	public boolean canBeParent() {
		return false;
	}
}