package at.ahammer.formyournotes.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.contact.Contact;
import at.ahammer.formyournotes.contact.ContactDao;
import at.ahammer.formyournotes.email.EMailIntent;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.maps.NaviIntent;
import at.ahammer.formyournotes.phone.PhoneIntent;
import at.ahammer.formyournotes.phone.PhoneIntent.Type;

public class ContactView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewText;
	private final TextView viewColon;
	private final EditText viewName;
	private final ImageButton chooseButton;
	private final ImageButton actionButton;
	private View.OnClickListener chooseButtonOnClickListener;
	private Dialog chooseContactDialog;
	private List<String> displayNames = new ArrayList<String>();
	private ContactDao contactDao = new ContactDao();

	public ContactView(final Context context, FormR r, FormBean formBean,
			ContactBean contactBean) {
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

		displayNames = contactDao.getAllDisplayNames(context);
		chooseContactDialog = createChooseContactDialog(context, displayNames);
		chooseButtonOnClickListener = createChooseButtonOnClickListener(context);
		chooseButton = new ImageButton(context);
		chooseButton.setImageResource(r.getDrawable().getButtonContact());
		chooseButton.setOnClickListener(chooseButtonOnClickListener);
		chooseButton.setBackground(null);

		actionButton = new ImageButton(context);
		actionButton.setImageResource(r.getDrawable().getButtonChat());
		actionButton.setBackground(null);
		actionButton.setOnClickListener(new ActionOnClickListener(viewName,
				context));

		addView(viewText, viewHelper.getLinearLayoutParamWrap());
		addView(viewColon, viewHelper.getLinearLayoutParamWrap());
		addView(viewName, viewHelper.getLinearLayoutParamWrap());
		addView(chooseButton, viewHelper.getLinearLayoutParamWrap());
		addView(actionButton, viewHelper.getLinearLayoutParamWrap());
	}

	private View.OnClickListener createChooseButtonOnClickListener(
			final Context context) {
		return new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				chooseContactDialog.show();
			}
		};
	}

	private Dialog createChooseContactDialog(Context context,
			List<String> displayNames) {
		Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle("Choose Contact");
		dialogBuilder.setItems(
				displayNames.toArray(new String[displayNames.size()]),
				new ChooseItemOnClickListener(this, displayNames));
		return dialogBuilder.create();
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

	private static class ChooseItemOnClickListener implements
			Dialog.OnClickListener {

		private final ContactView contactView;

		private final List<String> displayNames;

		public ChooseItemOnClickListener(ContactView contactView,
				List<String> displayNames) {
			this.contactView = contactView;
			this.displayNames = displayNames;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Log.i("FormYourNotes",
					"choose: " + which + " - " + displayNames.get(which));
			contactView.setName(displayNames.get(which));
			dialog.dismiss();
		}
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
			Log.i("FormYourNotes",
					"set value of " + contactBean.getDiscription() + " to '"
							+ displayName + "'");
			formBean.setDataChange(true);
			Log.i(LogTag.FYN.getTag(), "data changed because of contact-change");
			contactBean.getData().setItemId(contactBean);
			contactBean.getData().setDisplayName(displayName);

			Contact contact = contactDao.getContactByDisplayName(context,
					displayName);
			if (contact != null && contact.getId() > 0) {
				Log.i("FormYourNotes", "contact for '" + displayName + "':\n"
						+ contact.toString());
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
				Log.i("FormYourNotes", "unable to find contact for "
						+ displayName);
			}
		}
	}

	public static class ActionOnClickListener implements View.OnClickListener {

		private final EditText viewName;

		private final Context context;

		public ActionOnClickListener(EditText viewName, Context context) {
			this.viewName = viewName;
			this.context = context;
		}

		@Override
		public void onClick(View view) {
			String displayName = viewName.getText().toString();
			ContactDao contactDao = new ContactDao();
			Contact contact = contactDao.getContactByDisplayName(context,
					displayName);
			PopupMenu popupMenu = new PopupMenu(context, view);

			Set<String> menuPhoneItems = new HashSet<String>();
			Set<String> menuEmailItems = new HashSet<String>();
			Set<String> menuAddressItems = new HashSet<String>();
			if (contact != null && contact.getId() > 0) {
				Log.i("FormYourNotes", "contact for '" + displayName + "':\n"
						+ contact.toString());
				for (String phone : contact.getPhones()) {
					if (phone != null && !"".equals(phone)) {
						String phoneTel = "Tel: " + phone;
						popupMenu.getMenu().add(phoneTel);
						menuPhoneItems.add(phoneTel);
						String phoneSms = "SMS: " + phone;
						popupMenu.getMenu().add(phoneSms);
						menuPhoneItems.add(phoneSms);
					}
				}
				for (String email : contact.getEmails()) {
					if (email != null && !"".equals(email)) {
						popupMenu.getMenu().add(email);
						menuEmailItems.add(email);
					}
				}
				if (contact.getAddress() != null
						&& !"".equals(contact.getAddress())) {
					popupMenu.getMenu().add(contact.getAddress());
					menuAddressItems.add(contact.getAddress());
				}
			} else {
				Log.i("FormYourNotes", "unable to find contact for "
						+ displayName);
			}
			popupMenu
					.setOnMenuItemClickListener(new ActionOnMenuItemClickListener(
							context, menuPhoneItems, menuEmailItems,
							menuAddressItems));
			popupMenu.show();
		}
	}

	public static class ActionOnMenuItemClickListener implements
			PopupMenu.OnMenuItemClickListener {

		private final Context context;
		private final Set<String> phones = new HashSet<String>();
		private final Set<String> emails = new HashSet<String>();
		private final Set<String> addresses = new HashSet<String>();

		public ActionOnMenuItemClickListener(Context context,
				Set<String> phones, Set<String> emails, Set<String> addresses) {
			this.context = context;
			this.phones.addAll(phones);
			this.emails.addAll(emails);
			this.addresses.addAll(addresses);
		}

		@Override
		public boolean onMenuItemClick(MenuItem menuItem) {
			Log.i("FormYourNotes", "click menu item " + menuItem.getTitle());
			if (phones.contains(menuItem.getTitle())) {
				String phone = menuItem.getTitle().toString();
				Log.i("FormYourNotes", "menu item is a phone: " + phone);
				if (phone.startsWith("Tel: ")) {
					Log.i("FormYourNotes", "menu item is a phone tel: " + phone);
					phone = phone.substring(5);
					new PhoneIntent(context, phone, Type.TELEPHONE).perform();
				} else if (phone.startsWith("SMS: ")) {
					phone = phone.substring(5);
					Log.i("FormYourNotes", "menu item is a phone sms: " + phone);
					new PhoneIntent(context, phone, Type.SMS).perform();
				}
			} else if (emails.contains(menuItem.getTitle())) {
				String email = menuItem.getTitle().toString();
				Log.i("FormYourNotes", "menu item is an email: " + email);
				new EMailIntent(context, email).perform();
			} else if (addresses.contains(menuItem.getTitle())) {
				String address = menuItem.getTitle().toString();
				Log.i("FormYourNotes", "menu item is an address: " + address);
				new NaviIntent(context, address).perform();
			}
			return false;
		}
	}
}