package at.ahammer.formyournotes.views;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
	private final EditText viewName;
	private final ImageButton chooseButton;
	private final ImageButton actionButton;
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

		viewName = viewHelper.newDefaultEditText(context);
		viewName.setText(contactBean.getDisplayName());
		viewName.addTextChangedListener(new ContactWatcher(contactBean));

		displayNames = contactDao.getAllDisplayNames(context);
		chooseContactDialog = createChooseContactDialog(context, displayNames);
		chooseButtonOnClickListener = createChooseButtonOnClickListener(context);
		chooseButton = new ImageButton(context);
		chooseButton.setImageResource(r.getDrawable().getButtonEdit());
		chooseButton.setOnClickListener(chooseButtonOnClickListener);
		chooseButton.setBackground(null);
		chooseButton.setBackground(null);

		actionButton = new ImageButton(context);
		actionButton.setImageResource(r.getDrawable().getButtonDown());
		actionButton.setBackground(null);
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

		addView(viewText, viewHelper.getLinearLayoutParam());
		addView(viewColon, viewHelper.getLinearLayoutParam());
		addView(viewName, viewHelper.getLinearLayoutParam());
		addView(chooseButton, viewHelper.getLinearLayoutParam());
		addView(actionButton, viewHelper.getLinearLayoutParam());
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

		public ContactWatcher(ContactBean contactBean) {
			this.contactBean = contactBean;
		}

		@Override
		public void afterTextChanged(Editable editable) {
			Log.i("FormYourNotes",
					"set value of " + contactBean.getDiscription() + " to '"
							+ editable.toString() + "'");
			contactBean.getData().setItemId(contactBean);
			contactBean.getData().setDisplayName(editable.toString());
		}
	}
}