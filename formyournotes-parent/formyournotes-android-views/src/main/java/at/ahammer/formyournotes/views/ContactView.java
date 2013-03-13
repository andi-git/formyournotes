package at.ahammer.formyournotes.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.ContactBean;

public class ContactView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewText;
	private final TextView viewColon;
	private final TextView viewName;

	public ContactView(Context context, ContactBean contactBean) {
		super(context);
		setOrientation(HORIZONTAL);
		viewText = viewHelper.getDefaultTextView(context);
		viewText.setText(contactBean.getText());
		viewColon = viewHelper.getDefaultTextView(context);
		viewColon.setText(": ");

//		Uri myPhoneUri = Uri.withAppendedPath(
//				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//				Uri.encode(String.valueOf(contactBean.getContactId())));
//		Cursor phoneCursor = context.getContentResolver().query(myPhoneUri,
//				null, null, null, null);
//
//		String displayName = phoneCursor.getString(phoneCursor
//				.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//		Log.i("FormYourNotes",
//				"id "
//						+ contactBean.getContactId()
//						+ " -> "
//						+ displayName);
		// for (phoneCursor.moveToFirst(); !phoneCursor.isAfterLast();
		// phoneCursor
		// .moveToNext()) {
		// String phoneNumber = phoneCursor
		// .getString(phoneCursor
		// .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
		// sb.append("Phone: " + phoneNumber + "\n");
		// }
		viewName = viewHelper.getDefaultTextView(context);
//		viewName.setText(phoneCursor
//				.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

		addView(viewText, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(viewColon, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		addView(viewName, new LinearLayout.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	public String getName() {
		return viewName.getText().toString();
	}

	public String getText() {
		return viewText.getText().toString();
	}

	public void setName(String name) {
		viewName.setText(name);
	}

	public void setText(String text) {
		viewText.setText(text);
	}
}