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
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.util.FYNPreferences;

public class EditAccountDialog {

	private AlertDialog alertDialog;

	public EditAccountDialog(Activity activity) {
		alertDialog = create(activity);
		// NPE -> findViewById returns null
		// Account account = FYNPreferences.INSTANCE.getAccount(activity);
		// ((EditText)
		// activity.findViewById(R.id.email_input)).setText(account.getEmail());
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
		final View viewToInflate = inflater.inflate(
				R.layout.dialog_edit_account, null);
		builder.setView(viewToInflate)
				.setTitle(R.string.title_edit_account)
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
			String email = ((EditText) viewToInflate
					.findViewById(R.id.email_input)).getText().toString();
			String password = ((EditText) viewToInflate
					.findViewById(R.id.password_input)).getText().toString();
			Log.i(LogTag.FYN.getTag(), "Email: " + email);
			Log.i(LogTag.FYN.getTag(), "Password: " + password);
			FYNPreferences.INSTANCE.setAccount(activity, email, password);
			Toast.makeText(activity, R.string.change_account,
					Toast.LENGTH_SHORT).show();
		}
	}
}
