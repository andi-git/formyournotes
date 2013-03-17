package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.GroupData;

public class GroupBean extends FormYourNotesBean<GroupData> {

	private String name;

	private GroupData data = new GroupData();

	public GroupBean() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setData(GroupData data) {
		// nothing
	}

	@Override
	public GroupData getData() {
		return data;
	}

	@Override
	public boolean canBeParent() {
		return true;
	}
}
