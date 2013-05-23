package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.ahammer.formyournotes.data.SelectData;

public class SelectBean extends FormYourNotesBean<SelectData> {

	private String discription;

	private List<String> values = new ArrayList<String>();

	private SelectData data = new SelectData();

	public SelectBean() {
		super();
	}

	public SelectBean(String discription) {
		super();
		this.discription = discription;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public List<String> getValues() {
		return Collections.unmodifiableList(values);
	}

	public String[] getValuesArray() {
		return (String[]) values.toArray(new String[values.size()]);
	}

	public void setValues(List<String> values) {
		this.values.clear();
		this.values.addAll(values);
	}

	public void addValue(String value) {
		this.values.add(value);
	}

	public int getSelectedPosision() {
		int position = values.indexOf(data.getSelected());
		return position >= 0 ? position : 0;
	}

	@Override
	public void setData(SelectData data) {
		this.data = data;
	}

	@Override
	public SelectData getData() {
		return data;
	}

	@Override
	public void clearData() {
		data = new SelectData();
	}

	@Override
	public boolean canBeParent() {
		return false;
	}
}