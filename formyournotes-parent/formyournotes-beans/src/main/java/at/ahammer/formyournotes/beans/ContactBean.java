package at.ahammer.formyournotes.beans;

import java.util.List;

import at.ahammer.formyournotes.data.ContactData;

public class ContactBean extends FormYourNotesBean<ContactData> {

	private String discription;
	private ContactData data = new ContactData();

	public ContactBean() {
		super();
	}

	public ContactBean(String discription) {
		super();
		this.discription = discription;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getFirstName() {
		return data.getFirstName();
	}

	public void setFirstName(String firstName) {
		data.setFirstName(firstName);
	}

	public String getLastName() {
		return data.getLastName();
	}

	public void setLastName(String lastName) {
		data.setLastName(lastName);
	}

	public String getDisplayName() {
		return data.getDisplayName();
	}

	public void setDisplayName(String displayName) {
		data.setDisplayName(displayName);
	}

	public List<String> getPhones() {
		return data.getPhones();
	}

	public void setPhones(List<String> phones) {
		data.setPhones(phones);
	}

	public List<String> getEmails() {
		return data.getEmails();
	}

	public void setEmails(List<String> emails) {
		data.setEmails(emails);
	}

	public String getAddress() {
		return data.getAddress();
	}

	public void setAddress(String address) {
		data.setAddress(address);
	}

	@Override
	public void setData(ContactData data) {
		this.data = data;
	}

	@Override
	public ContactData getData() {
		return data;
	}

	@Override
	public void clearData() {
		data = new ContactData();
	}
	
	@Override
	public boolean canBeParent() {
		return false;
	}
}
