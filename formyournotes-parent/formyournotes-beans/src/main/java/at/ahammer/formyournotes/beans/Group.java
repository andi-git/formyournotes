package at.ahammer.formyournotes.beans;

public class Group extends FormYourNotesBean {

	private String name;

	public Group(int id, int rank, String name) {
		super(id, rank);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
