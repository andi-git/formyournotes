package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.GroupData;

public class GroupBean extends FormYourNotesBean<GroupData> {

	public static enum Orientation {
		VERTICAL, HORIZONTAL;
	}

	public static enum Border {
		NONE, TOP_ELEMENT, LIGHT_GRAY;
	}

	private String name;

	private Orientation orientation;

	private Border border;

	private GroupData data = new GroupData();

	public GroupBean() {
		super();
	}

	public GroupBean(String name, Orientation orientation, Border border) {
		super();
		this.name = name;
		this.orientation = orientation;
		this.border = border;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
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
	public void clearData() {
		data = new GroupData();
	}

	@Override
	public boolean canBeParent() {
		return true;
	}
}
