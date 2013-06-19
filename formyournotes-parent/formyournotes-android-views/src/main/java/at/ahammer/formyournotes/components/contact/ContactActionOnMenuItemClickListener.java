package at.ahammer.formyournotes.components.contact;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupMenu;
import at.ahammer.formyournotes.email.EMailIntent;
import at.ahammer.formyournotes.maps.NaviIntent;
import at.ahammer.formyournotes.phone.PhoneIntent;
import at.ahammer.formyournotes.phone.PhoneIntent.Type;

public class ContactActionOnMenuItemClickListener implements
		PopupMenu.OnMenuItemClickListener {

	private final Context context;
	private final Set<String> phones = new HashSet<String>();
	private final Set<String> emails = new HashSet<String>();
	private final Set<String> addresses = new HashSet<String>();

	public ContactActionOnMenuItemClickListener(Context context,
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