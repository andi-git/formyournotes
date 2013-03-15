package at.ahammer.formyournotes.data;

public class EditTextData extends FormYourNotesData {

	private String value;

	public EditTextData() {
		super();
	}

	public EditTextData(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
