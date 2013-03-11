package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

public class Form implements Identifiable {

	private int id;
	private String name;
	private List<Group> groups = new ArrayList<Group>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Group> getGroups() {
		return groups;
	}
}
