package at.ahammer.formyournotes.dao.json;

public class RequiredDataData {

	private int formId;
	private int dataId;

	public RequiredDataData(int formId, int dataId) {
		super();
		this.formId = formId;
		this.dataId = dataId;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

}
