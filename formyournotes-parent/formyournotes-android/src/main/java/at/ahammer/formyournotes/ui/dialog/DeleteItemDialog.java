package at.ahammer.formyournotes.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.ui.activity.FormFragmentLayout.FormFragmentLayoutIntent;
import at.ahammer.formyournotes.util.FYNController;

public class DeleteItemDialog {

	private AlertDialog alertDialog;

	public DeleteItemDialog(Activity activity) {
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
		final View viewToInflate = inflater.inflate(
				R.layout.dialog_delete_item, null);
		builder.setView(viewToInflate)
				.setTitle(R.string.title_delete_item)
				.setMessage(
						"Delete '"
								+ FYNController.INSTANCE.getCurrentFormData(
										activity).getName() + "'?")
				// Add action buttons
				.setPositiveButton(R.string.delete,
						new OnPositiveButtonClick(activity))
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

	private static class OnPositiveButtonClick implements
			DialogInterface.OnClickListener {

		private final Activity activity;

		private OnPositiveButtonClick(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(DialogInterface dialog, int id) {
			FormData currentData = FYNController.INSTANCE
					.getCurrentFormData(activity);
			FYNController.INSTANCE.delete(activity, currentData);
			Toast.makeText(activity, "delete '" + currentData.getName() + "'",
					Toast.LENGTH_SHORT).show();
			activity.finish();
			activity.startActivity(new FormFragmentLayoutIntent(activity).//
					setMessage("here goes a message").//
					build());
		}
	}
}
