package at.ahammer.formyournotes.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.ui.activity.FormFragmentLayout.FormFragmentLayoutIntent;
import at.ahammer.formyournotes.util.FYNController;

public class EditItemDialog {

	private AlertDialog alertDialog;

	public EditItemDialog(Activity activity) {
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
		final View viewToInflate = inflater.inflate(R.layout.dialog_edit_item,
				null);
		builder.setView(viewToInflate)
				.setTitle(
						"Edit Item: "
								+ FYNController.INSTANCE.getCurrentFormData(
										activity).getName())
				// Add action buttons
				.setPositiveButton(R.string.change,
						new OnPositivButtonClick(viewToInflate, activity))
				.setNegativeButton(R.string.cancel,
						new OnNegativeButtonClick(activity));
		return builder.create();
	}

	private static class OnNegativeButtonClick implements
			DialogInterface.OnClickListener {
		private final Activity activity;

		private OnNegativeButtonClick(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(DialogInterface dialog, int id) {
			Toast.makeText(activity, R.string.cancel, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private static class OnPositivButtonClick implements
			DialogInterface.OnClickListener {
		private final View viewToInflate;
		private final Activity activity;

		private OnPositivButtonClick(View viewToInflate, Activity activity) {
			this.viewToInflate = viewToInflate;
			this.activity = activity;
		}

		@Override
		public void onClick(DialogInterface dialog, int id) {
			EditText editText = (EditText) viewToInflate
					.findViewById(R.id.name_to_change_to);
			Log.i(LogTag.FYN.getTag(), "EditText: " + editText);
			String value = editText.getText().toString();
			try {
				FYNController.INSTANCE.updateFormData(activity, value);
				Toast.makeText(activity, "add " + value, Toast.LENGTH_SHORT)
						.show();
			} catch (DaoException e) {
				Toast.makeText(activity, "error: " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			activity.finish();
			activity.startActivity(new FormFragmentLayoutIntent(activity).//
					setMessage("here goes a message").//
					build());
		}
	}
}
