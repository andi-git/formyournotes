package at.ahammer.formyournotes.components.contact;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import at.ahammer.formyournotes.contact.ContactDao;

public class ContactChooseDialogFactory {

	public static Dialog createChooseContactDialog(Context context, EditText editText) {
		ContactDao contactDao = new ContactDao();
		List<String> displayNames = contactDao.getAllDisplayNames(context);

		Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle("Choose Contact");
		dialogBuilder.setItems(displayNames.toArray(new String[displayNames.size()]),
				new ContactChooseDialogOnClickListener(editText, displayNames));
		return dialogBuilder.create();
	}
}
