package at.ahammer.formyournotes.data;

import at.ahammer.formyournotes.beans.FormYourNotesBean;

public class FormYourNotesData {

	private int formId = -1;

	public FormYourNotesData() {
		super();
	}

	public FormYourNotesData(int formId) {
		super();
		this.formId = formId;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public void setFormId(FormYourNotesBean<?> formBean) {
		this.formId = formBean.getId();
	}
}
