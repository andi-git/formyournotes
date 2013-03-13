package at.ahammer.formyournotes.beans;

public class ContactBean extends FormYourNotesBean {

	private String text;
	private int contactId;

	public ContactBean(int id, int rank, String text, int contactId) {
		super(id, rank);
		this.text = text;
		this.contactId = contactId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

}
