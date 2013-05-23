package at.ahammer.formyournotes.data;

public class SelectData extends FormYourNotesData {

	private String selected;

	public SelectData() {
		super();
	}

	public SelectData(String selected, int formId) {
		super(formId);
		this.selected = selected;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public void setData(SelectData data) {
		super.setData(data);
		setSelected(data.getSelected());
	}
}
