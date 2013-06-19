package at.ahammer.formyournotes.components.contact;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import at.ahammer.formyournotes.contact.Contact;
import at.ahammer.formyournotes.contact.ContactDao;

public class ContactActionOnClickListener implements View.OnClickListener {

	private final EditText viewName;

	private final Context context;

	public ContactActionOnClickListener(EditText viewName, Context context) {
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
			Log.i("FormYourNotes", "unable to find contact for " + displayName);
		}
		popupMenu
				.setOnMenuItemClickListener(new ContactActionOnMenuItemClickListener(
						context, menuPhoneItems, menuEmailItems,
						menuAddressItems));
		popupMenu.show();
	}
}
