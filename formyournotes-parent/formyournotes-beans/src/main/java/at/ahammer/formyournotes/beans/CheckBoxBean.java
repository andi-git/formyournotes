package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.CheckBoxData;

public class CheckBoxBean extends FormYourNotesBean<CheckBoxData> {

	private String discription;

	private CheckBoxData data = new CheckBoxData();

	public CheckBoxBean() {
		super();
	}

	public CheckBoxBean(String discription) {
		super();
		this.discription = discription;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public boolean isChecked() {
		return data.isChecked();
	}

	public void setChecked(boolean checked) {
		data.setChecked(checked);
	}

	@Override
	public void setData(CheckBoxData data) {
		this.data = data;
	}

	@Override
	public CheckBoxData getData() {
		return data;
	}

	@Override
	public void clearData() {
		data = new CheckBoxData();
	}

	@Override
	public boolean canBeParent() {
		return false;
	}

}