package at.ahammer.formyournotes.dialog;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class DatePickerDialogFragment extends DialogFragment {

	private OnDateSetListener onSetListener;

	private Calendar date;
	
	public DatePickerDialogFragment() {
		// the default constructor is required to prevent the app from crashing
		// on device orientation changes
	}

	public DatePickerDialogFragment(OnDateSetListener callback, Calendar date) {
		this.onSetListener = (OnDateSetListener) callback;
		this.date = date;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.set(Calendar.YEAR, date.get(Calendar.YEAR));
			cal.set(Calendar.MONTH, date.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		}
		return new DatePickerDialog(getActivity(), onSetListener,
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
	}
}
