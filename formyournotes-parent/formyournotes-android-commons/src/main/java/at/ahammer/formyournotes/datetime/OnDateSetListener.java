package at.ahammer.formyournotes.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

public class OnDateSetListener implements DatePickerDialog.OnDateSetListener {

	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			"dd.MM.yyyy");
	private final TextView textView;

	public OnDateSetListener(TextView textView) {
		this.textView = textView;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		textView.setText(SDF.format(cal.getTime()));
	}
}
