package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

public class ContactBean extends FormYourNotesBean {

	private String discription;
	private String firstName;
	private String lastName;
	private String displayName;
	private List<String> phones = new ArrayList<String>();
	private List<String> emails = new ArrayList<String>();
	private String address;

	public ContactBean(int id, int rank, String discription, String firstName,
			String lastName) {
		super(id, rank);
		this.discription = discription;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
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
}