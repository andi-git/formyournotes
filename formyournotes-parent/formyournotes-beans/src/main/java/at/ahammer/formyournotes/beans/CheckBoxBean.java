package at.ahammer.formyournotes.beans;

public class CheckBoxBean extends FormYourNotesBean {

	private String text;

	private boolean checked;

	public CheckBoxBean(int id, int rank, String text, boolean checked) {
		super(id, rank);
		this.text = text;
		this.checked = checked;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}