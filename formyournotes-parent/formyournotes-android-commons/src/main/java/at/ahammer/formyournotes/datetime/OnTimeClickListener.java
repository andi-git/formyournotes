package at.ahammer.formyournotes.datetime;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

public class OnTimeClickListener implements View.OnClickListener {

	private final Activity activity;
	private final TextView textView;

	public OnTimeClickListener(Activity activity, TextView textView) {
		this.activity = activity;
		this.textView = textView;
	}

	@Override
	public void onClick(View view) {
		FragmentTransaction ft = activity.getFragmentManager()
				.beginTransaction();
		OnTimeSetListener onSetTimeListener = new OnTimeSetListener(textView);
		DialogFragment newFragment = new TimePickerDialogFragment(
				onSetTimeListener, textView.getText().toString());
		newFragment.show(ft, "time_dialog");
	}
}