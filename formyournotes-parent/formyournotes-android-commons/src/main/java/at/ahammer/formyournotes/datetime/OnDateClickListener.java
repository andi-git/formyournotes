package at.ahammer.formyournotes.datetime;

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
		DialogFragment newFragment = new DatePickerDialogFragment(
				onSetDateListener, DateHelper.parseCalendar(textView.getText().toString()));
		newFragment.show(ft, "date_dialog");
	}
}