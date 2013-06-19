package at.ahammer.formyournotes.views;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.components.contact.ContactActionOnClickListener;
import at.ahammer.formyournotes.components.contact.ContactChooseButtonOnClickListener;
import at.ahammer.formyournotes.contact.Contact;
import at.ahammer.formyournotes.contact.ContactDao;
import at.ahammer.formyournotes.logging.LogTag;

public class ContactView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewText;
	private final TextView viewColon;
	private final EditText viewName;
	private final ImageButton chooseButton;
	private final ImageButton actionButton;

	public ContactView(final Context context, FormR r, FormBean formBean, ContactBean contactBean) {
		super(context);
		setId(contactBean.getId());
		setOrientation(HORIZONTAL);
		viewText = viewHelper.newDefaultTextView(context);
		viewText.setText(contactBean.getDiscription());
		viewColon = viewHelper.newDefaultTextView(context);
		viewColon.setText(": ");

		viewName = viewHelper.newDefaultEditText(context);
		viewName.setText(contactBean.getDisplayName());
		viewName.addTextChangedListener(new ContactWatcher(contactBean, formBean, context));

		chooseButton = new ImageButton(context);
		chooseButton.setImageResource(r.getDrawable().getButtonContact());
		chooseButton.setOnClickListener(new ContactChooseButtonOnClickListener(context, viewName));
		chooseButton.setBackground(null);

		actionButton = new ImageButton(context);
		actionButton.setImageResource(r.getDrawable().getButtonChat());
		actionButton.setBackground(null);
		actionButton.setOnClickListener(new ContactActionOnClickListener(viewName, context));

		addView(viewText, viewHelper.getLinearLayoutParamWrap());
		addView(viewColon, viewHelper.getLinearLayoutParamWrap());
		addView(viewName, viewHelper.getLinearLayoutParamWrap());
		addView(chooseButton, viewHelper.getLinearLayoutParamWrap());
		addView(actionButton, viewHelper.getLinearLayoutParamWrap());
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

	public static class ContactWatcher extends AbstractTextWatcher {

		private final ContactBean contactBean;

		private final Context context;

		private final ContactDao contactDao;

		private final FormBean formBean;

		public ContactWatcher(ContactBean contactBean, FormBean formBean, Context context) {
			this.contactBean = contactBean;
			this.context = context;
			this.contactDao = new ContactDao();
			this.formBean = formBean;
		}

		@Override
		public void afterTextChanged(Editable editable) {
			String displayName = editable.toString();
			Log.i("FormYourNotes", "set value of " + contactBean.getDiscription() + " to '" + displayName + "'");
			formBean.setDataChange(true);
			Log.i(LogTag.FYN.getTag(), "data changed because of contact-change");
			contactBean.getData().setItemId(contactBean);
			contactBean.getData().setDisplayName(displayName);

			Contact contact = contactDao.getContactByDisplayName(context, displayName);
			if (contact != null && contact.getId() > 0) {
				Log.i("FormYourNotes", "contact for '" + displayName + "':\n" + contact.toString());
				contactBean.getData().setFirstName(contact.getFirstName());
				contactBean.getData().setLastName(contact.getLastName());
				contactBean.getData().setAddress(contact.getAddress());
				contactBean.getData().getPhones().clear();
				for (String phone : contact.getPhones()) {
					contactBean.getData().getPhones().add(phone);
				}
				contactBean.getData().getEmails().clear();
				for (String email : contact.getEmails()) {
					contactBean.getData().getEmails().add(email);
				}
			} else {
				Log.i("FormYourNotes", "unable to find contact for " + displayName);
			}
		}
	}
}