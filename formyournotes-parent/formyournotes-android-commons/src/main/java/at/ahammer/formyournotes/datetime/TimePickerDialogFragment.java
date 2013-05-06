package at.ahammer.formyournotes.datetime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import at.ahammer.formyournotes.datetime.TimeHelper.Time;

public class TimePickerDialogFragment extends DialogFragment {

	private OnTimeSetListener onSetListener;

	private String wellFormedTime;
	
	public TimePickerDialogFragment() {
		// the default constructor is required to prevent the app from crashing
		// on device orientation changes
	}

	public TimePickerDialogFragment(OnTimeSetListener callback, String wellFormedTime) {
		this.onSetListener = (OnTimeSetListener) callback;
		this.wellFormedTime = wellFormedTime;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Time time = TimeHelper.parseTime(wellFormedTime);
		return new TimePickerDialog(getActivity(), onSetListener, time.getHour(), time.getMinute(), true);
	}
}
