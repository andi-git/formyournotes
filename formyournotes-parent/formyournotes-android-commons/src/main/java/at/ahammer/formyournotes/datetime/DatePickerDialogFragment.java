package at.ahammer.formyournotes.datetime;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import at.ahammer.formyournotes.logging.LogTag;

public class DatePickerDialogFragment extends DialogFragment {

	private Calendar date;

	private TextView textView;

	public DatePickerDialogFragment() {
		// the default constructor is required to prevent the app from crashing
		// on device orientation changes
	}

	public DatePickerDialogFragment(Calendar date, TextView textView) {
		this.date = date;
		this.textView = textView;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.set(Calendar.YEAR, date.get(Calendar.YEAR));
			cal.set(Calendar.MONTH, date.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
		}
		DatePickerDialog dialog = new DatePickerDialog(getActivity(), null, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatePicker datePicker = ((DatePickerDialog) dialog).getDatePicker();
				int year = datePicker.getYear();
				int month = datePicker.getMonth();
				int day = datePicker.getDayOfMonth();
				String formattedCalendar = DateHelper.formatCalendar(year, month, day);
				Log.i(LogTag.FYN.getTag(), "Date: " + formattedCalendar);
				textView.setText(formattedCalendar);
			}
		});
		dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				textView.setText(DateHelper.DEFAULT_DATE);
			}
		});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// nothing
			}
		});
		return dialog;
	}
}
