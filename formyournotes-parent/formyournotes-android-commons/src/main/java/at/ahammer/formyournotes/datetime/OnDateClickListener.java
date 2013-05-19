package at.ahammer.formyournotes.datetime;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

public class OnDateClickListener implements View.OnClickListener {

	private final Activity activity;
	private final TextView textView;

	public OnDateClickListener(Activity activity, TextView textView) {
		this.activity = activity;
		this.textView = textView;
	}

	@Override
	public void onClick(View view) {
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		OnDateSetListener onSetDateListener = new OnDateSetListener(textView);
		Calendar calendarValue = getCalendar();
		DialogFragment newFragment = new DatePickerDialogFragment(
				onSetDateListener, calendarValue);
		newFragment.show(ft, "date_dialog");
	}

	private Calendar getCalendar() {
		String calendarString = textView.getText().toString();
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("Europe/Berlin"));
		if (!"00.00.0000".equals(calendarString)) {
			calendar = DateHelper.parseCalendar(calendarString);
		}
		return calendar;
	}

}