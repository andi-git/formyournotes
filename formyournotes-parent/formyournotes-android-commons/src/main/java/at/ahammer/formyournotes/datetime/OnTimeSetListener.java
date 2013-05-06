package at.ahammer.formyournotes.datetime;

import android.app.TimePickerDialog;
import android.widget.TextView;
import android.widget.TimePicker;

public class OnTimeSetListener implements TimePickerDialog.OnTimeSetListener {

	private final TextView textView;

	public OnTimeSetListener(TextView textView) {
		this.textView = textView;
	}

	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		textView.setText(TimeHelper.formatTime(hour, minute));
	}
}
