package at.ahammer.formyournotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import at.ahammer.formyournotes.FragmentLayout.FragmentLayoutIntentBuilder;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.json.DataDaoJSON;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.logging.LogTag;

public class AddItemDialog {

	private AlertDialog alertDialog;

	private DataDao dataDao;

	public AddItemDialog(Activity activity) {
		alertDialog = create(activity);
		dataDao = new DataDaoJSON(FYNFileHelper.getExternalStorage(activity));
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
		final View viewToInflate = inflater.inflate(R.layout.dialog_add_item,
				null);
		builder.setView(viewToInflate)
				.setTitle(R.string.title_add_item)
				// Add action buttons
				.setPositiveButton(R.string.add,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								EditText editText = (EditText) viewToInflate
										.findViewById(R.id.itemName);
								Log.i(LogTag.FYN.getTag(), "EditText: "
										+ editText);
								String value = editText.getText().toString();
								try {
									FormData formData = new FormData();
									formData.setFormId(FormYourNotesController.INSTANCE
											.getFormId());
									formData.setName(value);
									dataDao.save(formData);
									Toast.makeText(activity, "add " + value,
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
											"exception occurs on adding"
													+ value, Toast.LENGTH_SHORT)
											.show();
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
