package at.ahammer.formyournotes.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.components.contact.ContactChooseButtonOnClickListener;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.ui.activity.FormFragmentLayout.FormFragmentLayoutIntent;
import at.ahammer.formyournotes.util.FYNController;

public class AddItemDialog {

	private AlertDialog alertDialog;

	public AddItemDialog(Activity activity) {
		alertDialog = create(activity);
	}

	public void show() {
		alertDialog.show();
	}

	private AlertDialog create(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		// Get the layout inflater
		LayoutInflater inflater = activity.getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		final View viewToInflate = inflater.inflate(R.layout.dialog_add_item, null);
		builder.setView(viewToInflate).setTitle(R.string.title_add_item)
				// Add action buttons
				.setPositiveButton(R.string.add, new OnPositivButtonClick(viewToInflate, activity))
				.setNegativeButton(R.string.cancel, new OnNegativeButtonClick(activity));

		EditText editText = (EditText) viewToInflate.findViewById(R.id.itemName);
		ImageButton chooseContact = (ImageButton) viewToInflate.findViewById(R.id.addItemChooseContact);
		chooseContact.setOnClickListener(new ContactChooseButtonOnClickListener(activity, editText));

		return builder.create();
	}

	private static class OnNegativeButtonClick implements DialogInterface.OnClickListener {
		private final Activity activity;

		private OnNegativeButtonClick(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(DialogInterface dialog, int id) {
			Toast.makeText(activity, R.string.cancel, Toast.LENGTH_SHORT).show();
		}
	}

	private static class OnPositivButtonClick implements DialogInterface.OnClickListener {
		private final View viewToInflate;
		private final Activity activity;

		private OnPositivButtonClick(View viewToInflate, Activity activity) {
			this.viewToInflate = viewToInflate;
			this.activity = activity;
		}

		@Override
		public void onClick(DialogInterface dialog, int id) {
			EditText editText = (EditText) viewToInflate.findViewById(R.id.itemName);
			Log.i(LogTag.FYN.getTag(), "EditText: " + editText);
			String value = editText.getText().toString();
			if (value != null && !"".equals(value)) {
				FormData formData = new FormData();
				formData.setFormId(FYNController.INSTANCE.getFormId());
				formData.setName(value);
				try {
					FYNController.INSTANCE.saveFormData(activity, formData);
					Toast.makeText(activity, "add " + value, Toast.LENGTH_SHORT).show();
				} catch (DaoException e) {
					Toast.makeText(activity, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(activity, "error: name is empty", Toast.LENGTH_SHORT).show();
			}
			activity.finish();
			activity.startActivity(new FormFragmentLayoutIntent(activity).//
					setMessage("here goes a message").//
					build());
		}
	}
}
