package at.ahammer.formyournotes.data;

import at.ahammer.formyournotes.beans.FormYourNotesBean;

public class FormYourNotesData {

	private int itemId = -1;

	public FormYourNotesData() {
		super();
	}

	public FormYourNotesData(int itemId) {
		super();
		this.itemId = itemId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setItemId(FormYourNotesBean<?> formBean) {
		this.itemId = formBean.getId();
	}

	public void setData(FormYourNotesData data) {
		this.itemId = data.getItemId();
	}

	public boolean isFilled() {
		return itemId > 0;
	}
}
