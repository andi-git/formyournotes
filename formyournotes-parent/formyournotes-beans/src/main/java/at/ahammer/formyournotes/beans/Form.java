package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

public class Form extends FormYourNotesBean {

	private String name;
	private List<Group> groups = new ArrayList<Group>();

	public Form(int id, int rank, String name) {
		super(id, rank);
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
