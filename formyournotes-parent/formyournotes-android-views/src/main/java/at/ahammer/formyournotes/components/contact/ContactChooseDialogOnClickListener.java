package at.ahammer.formyournotes.components.contact;

import java.util.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;

public class ContactChooseDialogOnClickListener implements
		Dialog.OnClickListener {

	private final EditText editText;

	private final List<String> displayNames;

	public ContactChooseDialogOnClickListener(EditText editText,
			List<String> displayNames) {
		this.editText = editText;
		this.displayNames = displayNames;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		Log.i("FormYourNotes",
				"choose: " + which + " - " + displayNames.get(which));
		editText.setText(displayNames.get(which));
		dialog.dismiss();
	}
}