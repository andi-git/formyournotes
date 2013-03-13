package at.ahammer.formyournotes.beans;

public class FormYourNotesBean {

	private int id;

	private int rank;

	public FormYourNotesBean(int id, int rank) {
		super();
		this.id = id;
		this.rank = rank;
	}

	public int getId() {
		return id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setId(int id) {
		this.id = id;
	}
}
