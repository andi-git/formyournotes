package at.ahammer.formyournotes.components.contact;

import android.app.Dialog;
import android.view.View;

public class ContactChooseButtonOnClickListener implements View.OnClickListener {

	private final Dialog chooseContactDialog;

	public ContactChooseButtonOnClickListener(Dialog chooseContactDialog) {
		this.chooseContactDialog = chooseContactDialog;
	}

	@Override
	public void onClick(View arg0) {
		chooseContactDialog.show();
	}
}
