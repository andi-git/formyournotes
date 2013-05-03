package at.ahammer.formyournotes.datetime;

import java.text.ParseException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import at.ahammer.formyournotes.logging.LogTag;

public class OnClickCalendarListener implements View.OnClickListener {

	private final Activity activity;
	private final TextView textView;

	public OnClickCalendarListener(Activity activity, TextView textView) {
		this.activity = activity;
		this.textView = textView;
	}

	@Override
	public void onClick(View view) {
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		OnDateSetListener onSetDateListener = new OnDateSetListener(textView);
		DialogFragment newFragment = new DatePickerDialogFragment(
				onSetDateListener, getCalendar());
		newFragment.show(ft, "date_dialog");
	}

	private Calendar getCalendar() {
		Calendar date = Calendar.getInstance();
		try {
			date.setTime(OnDateSetListener.SDF.parse(textView.getText()
					.toString()));
		} catch (ParseException e) {
			Log.i(LogTag.FYN.getTag(), "error on parsing calendar: "
					+ textView.getText().toString());
		}
		return date;
	}
}