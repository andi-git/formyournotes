package at.ahammer.formyournotes.dao;

import java.util.ArrayList;
import java.util.List;

public class Contact {

	private int id;
	private String firstName;
	private String lastName;
	private String displayName;
	private List<String> phones = new ArrayList<String>();
	private List<String> emails = new ArrayList<String>();
	private String address;

	public Contact() {
		// nothing
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(", ");
		sb.append(displayName);
		sb.append(", ");
		sb.append(firstName);
		sb.append(", ");
		sb.append(lastName);
		for (String phone : phones) {
			sb.append(", ");
			sb.append(phone);
		}
		for (String email : emails) {
			sb.append(", ");
			sb.append(email);
		}
		sb.append(", ");
		sb.append(address);
		return sb.toString();
	}
}
