package at.ahammer.formyournotes.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.CalendarBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.dialog.DatePickerDialogFragment;
import at.ahammer.formyournotes.logging.LogTag;

public class CalendarView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;
	private final TextView viewColon;
	private final TextView viewText;
	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			"dd.MM.yyyy");

	public CalendarView(final Activity activity, FormR r,
			final FormBean formBean, final CalendarBean calendarBean) {
		super(activity);
		setOrientation(HORIZONTAL);
		viewName = viewHelper.newDefaultTextView(activity);
		viewName.setText(calendarBean.getDiscription());
		viewText = viewHelper.newDefaultTextView(activity);
		viewText.setText(calendarBean.getValue());
		viewText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentTransaction ft = activity.getFragmentManager()
						.beginTransaction();
				Calendar date = Calendar.getInstance();
				try {
					date.setTime(SDF.parse(calendarBean.getData().getValue()));
				} catch (ParseException e) {
					Log.e(LogTag.FYN.getTag(), "error on parsing calendar: "
							+ calendarBean.getData().getValue(), e);
				}
				DialogFragment newFragment = new DatePickerDialogFragment(
						new OnSetDateWatcher(calendarBean, formBean, viewText),
						date);
				newFragment.show(ft, "date_dialog");
			}
		});
		viewColon = viewHelper.newDefaultTextView(activity);
		viewColon.setText(": ");
		addView(viewName, viewHelper.getLinearLayoutParamWrap());
		addView(viewColon, viewHelper.getLinearLayoutParamWrap());
		addView(viewText, viewHelper.getLinearLayoutParamWrap());
	}

	public String getName() {
		return viewName.getText().toString();
	}

	public String getText() {
		return viewText.getText().toString();
	}

	public void setName(String name) {
		viewName.setText(name);
	}

	public void setText(String text) {
		viewText.setText(text);
	}

	public static class OnSetDateWatcher implements
			DatePickerDialog.OnDateSetListener {

		private final CalendarBean calendarBean;
		private final FormBean formBean;
		private final TextView textView;

		public OnSetDateWatcher(CalendarBean calendarBean, FormBean formBean,
				TextView textView) {
			this.calendarBean = calendarBean;
			this.formBean = formBean;
			this.textView = textView;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
			String dateString = SDF.format(cal.getTime());
			textView.setText(dateString);

			Log.i(LogTag.FYN.getTag(),
					"set value of " + calendarBean.getDiscription() + " to '"
							+ dateString + "'");
			calendarBean.getData().setItemId(calendarBean);
			// TODO why do i have to set that hard-coded?
			formBean.setDataChange(true);
			// if
			// (formBean.possibleDataChange(calendarBean.getData().getValue(),
			// dateString)) {
			// Log.i(LogTag.FYN.getTag(),
			// "data changed from "
			// + calendarBean.getData().getValue() + " to "
			// + dateString);
			// }
			calendarBean.getData().setValue(dateString);
		}
	}
}