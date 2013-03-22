package at.ahammer.formyournotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import at.ahammer.formyournotes.FragmentLayout.FragmentLayoutIntentBuilder;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.logging.LogTag;

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
				.setMessage("Delete '" + FormYourNotesController.INSTANCE
						.getCurrentFormData(activity).getName() + "'?")
				// Add action buttons
				.setPositiveButton(R.string.delete,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								FormData currentData = FormYourNotesController.INSTANCE
										.getCurrentFormData(activity);
								try {
									FormYourNotesController.INSTANCE.getDataDao(activity)
											.delete(currentData);
									Toast.makeText(activity,
											"delete '" + currentData.getName() + "'",
											Toast.LENGTH_SHORT).show();
									activity.finish();
									activity.startActivity(new FragmentLayoutIntentBuilder(
											activity).//
											setMessage("here goes a message").//
											getIntent());
								} catch (DaoException e) {
									Log.e(LogTag.FYN.getTag(), e.getMessage(),
											e);
									Toast.makeText(
											activity,
											"exception occurs on deleting"
													+ currentData.getName(),
											Toast.LENGTH_SHORT).show();
								}
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Toast.makeText(activity, R.string.cancel,
										Toast.LENGTH_SHORT).show();
							}
						});
		return builder.create();
	}
	
	
}
