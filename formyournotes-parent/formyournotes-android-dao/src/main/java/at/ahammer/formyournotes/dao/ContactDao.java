package at.ahammer.formyournotes.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactDao {

	// Data
	private static final Uri DATA_CONTENT_URI = ContactsContract.Data.CONTENT_URI;
	private static final String DATA_MIMETYPE = ContactsContract.Data.MIMETYPE;
	private static final String DATA_CONTACT_ID = ContactsContract.Data.CONTACT_ID;
	// Nmae
	private static final String NAME_CONTENT_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE;
	private static final String NAME_CONTACT_ID = ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID;
	private static final String NAME_DISPLAY_NAME = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME;
	private static final String NAME_FAMILY_NAME = ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME;
	private static final String NAME_GIVEN_NAME = ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME;
	// Phone
	private static final Uri PHONE_CONTENT = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
	private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
	private static final String PHONE_CONTENT_ITEM_TYPE = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
	// Email
	private static final Uri EMAIL_CONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
	private static final String EMAIL_CONTENT_ITEM_TYPE = ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE;
	private static final String EMAIL_CONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
	private static final String EMAIL_ADDRESS = ContactsContract.CommonDataKinds.Email.ADDRESS;
	// Postal
	private static final Uri POSTAL_CONTENT_URI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
	private static final String POSTAL_CONTENT_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE;
	private static final String POSTAL_CONTACT_ID = ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID;
	private static final String POSTAL_FORMATTED_ADDRESS = ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS;

	public List<Contact> getAllContacts(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(PHONE_CONTENT, null, null, null,
				null);

		Set<Integer> ids = new HashSet<Integer>();
		while (cursor.moveToNext()) {
			String contactId = cursor.getString(cursor
					.getColumnIndex(DATA_CONTACT_ID));
			ids.add(Integer.valueOf(contactId));
		}
		cursor.close();

		List<Integer> sortedIds = new ArrayList<Integer>(ids);
		Collections.sort(sortedIds);
		List<Contact> contacts = new ArrayList<Contact>();
		for (Integer sortedId : sortedIds) {
			Contact contact = new Contact();
			contact.setId(sortedId.intValue());
			addName(context, contact);
			addPhones(context, contact);
			addEmails(context, contact);
			addAddress(context, contact);
			contacts.add(contact);
		}
		return contacts;
	}

	private void addName(Context context, Contact contact) {
		String[] projection = new String[] { NAME_GIVEN_NAME, NAME_FAMILY_NAME,
				NAME_DISPLAY_NAME };

		String selection = DATA_MIMETYPE + " = ? AND " + NAME_CONTACT_ID
				+ " = ?";
		String[] selectionArgs = new String[] { NAME_CONTENT_ITEM_TYPE,
				String.valueOf(contact.getId()) };
		String sortOrder = NAME_DISPLAY_NAME;

		Cursor cursor = context.getContentResolver().query(DATA_CONTENT_URI,
				projection, selection, selectionArgs, sortOrder);

		if (cursor != null && cursor.isBeforeFirst() && !cursor.isAfterLast()) {
			cursor.moveToNext();
			contact.setFirstName(cursor.getString(cursor
					.getColumnIndex(NAME_GIVEN_NAME)));
			contact.setLastName(cursor.getString(cursor
					.getColumnIndex(NAME_FAMILY_NAME)));
			contact.setDisplayName(cursor.getString(cursor
					.getColumnIndex(NAME_DISPLAY_NAME)));
		}

		cursor.close();
	}

	private void addPhones(Context context, Contact contact) {
		String[] projection = new String[] { PHONE_NUMBER };
		String selection = DATA_MIMETYPE + " = ? AND " + PHONE_CONTACT_ID
				+ " = ?";
		String[] selectionArgs = new String[] { PHONE_CONTENT_ITEM_TYPE,
				String.valueOf(contact.getId()) };
		String sortOrder = PHONE_NUMBER;

		Cursor cursor = context.getContentResolver().query(PHONE_CONTENT,
				projection, selection, selectionArgs, sortOrder);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				contact.getPhones().add(
						cursor.getString(cursor.getColumnIndex(PHONE_NUMBER)));
			}
		}
		cursor.close();
	}

	private void addEmails(Context context, Contact contact) {
		String[] projection = new String[] { EMAIL_ADDRESS };
		String selection = DATA_MIMETYPE + " = ? AND " + EMAIL_CONTACT_ID
				+ " = ?";
		String[] selectionArgs = new String[] { EMAIL_CONTENT_ITEM_TYPE,
				String.valueOf(contact.getId()) };
		String sortOrder = EMAIL_ADDRESS;

		Cursor cursor = context.getContentResolver().query(EMAIL_CONTENT_URI,
				projection, selection, selectionArgs, sortOrder);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				contact.getEmails().add(
						cursor.getString(cursor.getColumnIndex(EMAIL_ADDRESS)));
			}
		}
		cursor.close();
	}

	private void addAddress(Context context, Contact contact) {
		String[] projection = new String[] { POSTAL_FORMATTED_ADDRESS };
		String selection = DATA_MIMETYPE + " = ? AND " + POSTAL_CONTACT_ID
				+ " = ?";
		String[] selectionArgs = new String[] { POSTAL_CONTENT_ITEM_TYPE,
				String.valueOf(contact.getId()) };
		String sortOrder = null;

		Cursor cursor = context.getContentResolver().query(POSTAL_CONTENT_URI,
				projection, selection, selectionArgs, sortOrder);

		if (cursor != null && cursor.isBeforeFirst() && !cursor.isAfterLast()) {
			cursor.moveToNext();
			contact.setAddress(cursor.getString(cursor
					.getColumnIndex(POSTAL_FORMATTED_ADDRESS)));
		}

		cursor.close();
	}

	public List<String> getAllDisplayNames(Context context) {
		String[] projection = new String[] { NAME_DISPLAY_NAME };
		String selection = DATA_MIMETYPE + " = ?";
		String[] selectionArgs = new String[] { NAME_CONTENT_ITEM_TYPE };
		String sortOrder = NAME_DISPLAY_NAME;

		Cursor cursor = context.getContentResolver().query(DATA_CONTENT_URI,
				projection, selection, selectionArgs, sortOrder);

		List<String> displayNames = new ArrayList<String>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String displayName = cursor.getString(cursor
						.getColumnIndex(NAME_DISPLAY_NAME));
				if (displayName == null) {
					displayName = "unknown";
				}
				displayNames.add(displayName);
			}
		}
		cursor.close();
		return displayNames;
	}
}
