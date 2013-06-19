package at.ahammer.formyournotes.components.contact;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

public class ContactChooseButtonOnClickListener implements View.OnClickListener {

	private final Dialog chooseContactDialog;

	public ContactChooseButtonOnClickListener(Context context, EditText editText) {
		this.chooseContactDialog = ContactChooseDialogFactory.createChooseContactDialog(context, editText);
	}

	@Override
	public void onClick(View arg0) {
		chooseContactDialog.show();
	}
}
