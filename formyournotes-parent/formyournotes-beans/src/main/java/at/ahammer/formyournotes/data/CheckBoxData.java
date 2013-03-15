package at.ahammer.formyournotes.data;

public class CheckBoxData extends FormYourNotesData {

	private boolean checked;

	public CheckBoxData() {
		super();
	}

	public CheckBoxData(boolean checked) {
		super();
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
