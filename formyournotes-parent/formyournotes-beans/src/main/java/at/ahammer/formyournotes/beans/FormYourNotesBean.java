package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.FormYourNotesData;

public abstract class FormYourNotesBean <T extends FormYourNotesData> {

	private int id = 0;

	private int rank = 0;

	private int parent = 0;

	public FormYourNotesBean() {
		super();
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

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public void setParent(FormYourNotesBean<?> bean) {
		this.parent = bean.getId();
	}

	public boolean hasParent() {
		return parent > 0;
	}

	public abstract void setData(T data);

	public abstract T getData();
	
	public abstract void clearData();
	
	public abstract boolean canBeParent();

	public boolean isTopLevelElement() {
		return parent == 0;
	}
}
