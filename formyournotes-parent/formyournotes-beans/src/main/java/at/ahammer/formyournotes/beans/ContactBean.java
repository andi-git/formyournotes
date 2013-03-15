package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

import at.ahammer.formyournotes.data.ContactData;

public class ContactBean extends FormYourNotesBean<ContactData> {

	private String discription;
	private String firstName;
	private String lastName;
	private String displayName;
	private List<String> phones = new ArrayList<String>();
	private List<String> emails = new ArrayList<String>();
	private String address;

	public ContactBean() {
		super();
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

	@Override
	public void addData(ContactData data) {
		firstName = data.getFirstName();
		lastName = data.getLastName();
		displayName = data.getDisplayName();
		phones = new ArrayList<String>();
		for (String phone : data.getPhones()) {
			phones.add(phone);
		}
		emails = new ArrayList<String>();
		for (String email : data.getEmails()) {
			emails.add(email);
		}
		address = data.getAddress();
	}

	@Override
	public ContactData getData() {
		ContactData data = new ContactData();
		data.setFirstName(firstName);
		data.setLastName(lastName);
		data.setDisplayName(displayName);
		for (String phone : phones) {
			data.getPhones().add(phone);
		}
		for (String email : emails) {
			data.getEmails().add(email);
		}
		data.setAddress(address);
		return null;
	}

	@Override
	public boolean canBeParent() {
		return false;
	}
}
