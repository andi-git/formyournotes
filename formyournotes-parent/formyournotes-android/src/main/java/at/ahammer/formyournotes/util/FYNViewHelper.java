package at.ahammer.formyournotes.util;

import android.content.Context;
import android.widget.Toast;
import at.ahammer.formyournotes.dao.DaoException;

public enum FYNViewHelper {

	INSTANCE;

	public void saveCurrentForm(Context context) {
		try {
			if (FYNController.INSTANCE.updateFormData(context)) {
				Toast.makeText(context, "Save Form Data", Toast.LENGTH_SHORT)
						.show();
			}
		} catch (DaoException e) {
			Toast.makeText(context, "Error on saving: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}
}
