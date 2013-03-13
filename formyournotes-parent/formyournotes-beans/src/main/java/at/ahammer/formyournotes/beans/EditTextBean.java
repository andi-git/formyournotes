package at.ahammer.formyournotes.beans;

public class EditTextBean extends FormYourNotesBean {

	private String name;

	private String text;

	public EditTextBean(int id, int rank, String name, String text) {
		super(id, rank);
		this.name = name;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}