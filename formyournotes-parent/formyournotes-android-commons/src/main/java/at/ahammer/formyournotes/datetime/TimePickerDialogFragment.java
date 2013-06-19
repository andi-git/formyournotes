package at.ahammer.formyournotes.datetime;

import java.lang.reflect.Field;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;
import at.ahammer.formyournotes.datetime.TimeHelper.Time;
import at.ahammer.formyournotes.logging.LogTag;

public class TimePickerDialogFragment extends DialogFragment {

	private TextView textView;

	public TimePickerDialogFragment() {
		// the default constructor is required to prevent the app from crashing
		// on device orientation changes
	}

	public TimePickerDialogFragment(TextView textView) {
		this.textView = textView;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Time time = TimeHelper.currentTime();
		if (getWellFormedTime() != null && !"".equals(getWellFormedTime())
				&& !TimeHelper.DEFAULT_TIME.equals(getWellFormedTime())) {
			time = TimeHelper.parseTime(getWellFormedTime());
		}
		TimePickerDialog dialog = new TimePickerDialog(getActivity(), null, time.getHour(), time.getMinute(), true);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int hour = 0;
				int minute = 0;
				try {
					Field timePickerField = ((TimePickerDialog) dialog).getClass().getDeclaredField("mTimePicker");
					timePickerField.setAccessible(true);
					TimePicker timePicker = (TimePicker) timePickerField.get(dialog);
					hour = timePicker.getCurrentHour();
					minute = timePicker.getCurrentMinute();
				} catch (Exception e) {
					Log.e(LogTag.FYN.getTag(), e.getMessage());
				}
				String formattedTime = TimeHelper.formatTime(hour, minute);
				Log.i(LogTag.FYN.getTag(), "Time: " + formattedTime);
				textView.setText(formattedTime);
			}
		});
		dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				textView.setText(TimeHelper.DEFAULT_TIME);
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

	private String getWellFormedTime() {
		return textView.getText().toString();
	}
}
