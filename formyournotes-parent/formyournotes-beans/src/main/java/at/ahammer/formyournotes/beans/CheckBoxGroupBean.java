package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

import at.ahammer.formyournotes.data.CheckBoxGroupData;

public class CheckBoxGroupBean extends FormYourNotesBean<CheckBoxGroupData> {

	private String discription;

	private List<CheckBoxBean> checkBoxes = new ArrayList<CheckBoxBean>();

	private CheckBoxGroupData data = new CheckBoxGroupData();
	
	public CheckBoxGroupBean() {
		super();
	}

	public CheckBoxGroupBean(String discription) {
		super();
		this.discription = discription;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public List<CheckBoxBean> getCheckBoxes() {
		return checkBoxes;
	}

	public void addCheckBox(CheckBoxBean checkBox) {
		checkBoxes.add(checkBox);
	}

	@Override
	public void setData(CheckBoxGroupData data) {
		// nothing
	}

	@Override
	public CheckBoxGroupData getData() {
		return data;
	}

	@Override
	public void clearData() {
		data = new CheckBoxGroupData();
	}
	
	@Override
	public boolean canBeParent() {
		return false;
	}
}