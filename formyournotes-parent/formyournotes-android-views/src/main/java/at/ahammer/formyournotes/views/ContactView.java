package at.ahammer.formyournotes.views;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.dao.ContactDao;

public class ContactView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewText;
	private final TextView viewColon;
	private final TextView viewName;
	private final Button chooseButton;
	private final Button actionButton;
	private View.OnClickListener chooseButtonOnClickListener;
	private Dialog chooseContactDialog;
	private List<String> displayNames = new ArrayList<String>();
	private ContactDao contactDao = new ContactDao();

	public ContactView(final Context context, MyR r, FormBean formBean,
			ContactBean contactBean) {
		super(context);
		setOrientation(HORIZONTAL);
		viewText = viewHelper.newDefaultTextView(context);
		viewText.setText(contactBean.getDiscription());
		viewColon = viewHelper.newDefaultTextView(context);
		viewColon.setText(": ");

		viewName = viewHelper.newDefaultTextView(context);
		viewName.setText("---");

		displayNames = contactDao.getAllDisplayNames(context);
		chooseContactDialog = createChooseContactDialog(context, displayNames);
		chooseButtonOnClickListener = createChooseButtonOnClickListener(context);
		chooseButton = viewHelper.newDefaultButton(context);
		chooseButton.setText("Choose");
		chooseButton.setOnClickListener(chooseButtonOnClickListener);

		actionButton = viewHelper.newDefaultButton(context);
		actionButton.setText("Action");
		actionButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				PopupMenu popup = new PopupMenu(context, view);
				popup.getMenu().add("Call");
				popup.getMenu().add("Email");
				popup.getMenu().add("Navi");
				popup.show();
			}
		});

		addView(viewText, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(viewColon, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(viewName, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(chooseButton, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addView(actionButton, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
}