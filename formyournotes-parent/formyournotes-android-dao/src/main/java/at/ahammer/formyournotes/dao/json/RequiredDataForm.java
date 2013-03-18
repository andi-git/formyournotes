package at.ahammer.formyournotes.dao.json;

public class RequiredDataForm {

	private int formId;

	public RequiredDataForm(int formId) {
		super();
		this.formId = formId;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}
}
