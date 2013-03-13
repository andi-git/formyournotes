package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxGroupBean extends FormYourNotesBean {

	private String text;

	private List<CheckBoxBean> checkBoxes = new ArrayList<CheckBoxBean>();

	public CheckBoxGroupBean(int id, int rank, String text) {
		super(id, rank);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<CheckBoxBean> getCheckBoxes() {
		return checkBoxes;
	}

	public void setCheckBoxes(List<CheckBoxBean> checkBoxes) {
		this.checkBoxes = checkBoxes;
	}
}