package at.ahammer.formyournotes.components.contact;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;

public class ContactChooseDialogFactory {

	public static Dialog createChooseContactDialog(Context context,
			EditText editText, List<String> displayNames) {
		Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle("Choose Contact");
		dialogBuilder.setItems(
				displayNames.toArray(new String[displayNames.size()]),
				new ContactChooseDialogOnClickListener(editText, displayNames));
		return dialogBuilder.create();
	}
}
