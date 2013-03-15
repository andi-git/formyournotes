package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.CheckBoxData;

public class CheckBoxBean extends FormYourNotesBean<CheckBoxData> {

	private String discription;

	private boolean checked;

	public CheckBoxBean() {
		super();
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public void addData(CheckBoxData data) {
		checked = data.isChecked();
	}

	@Override
	public CheckBoxData getData() {
		CheckBoxData data = new CheckBoxData();
		data.setChecked(checked);
		return data;
	}

	@Override
	public boolean canBeParent() {
		return false;
	}
}