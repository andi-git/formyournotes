package at.ahammer.formyournotes.views;

import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.CalendarBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.datetime.OnClickCalendarListener;

public class CalendarView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;
	private final TextView viewColon;
	private final TextView viewText;

	public CalendarView(final Activity activity, FormR r,
			final FormBean formBean, final CalendarBean calendarBean) {
		super(activity);
		setId(calendarBean.getId());
		setOrientation(HORIZONTAL);
		viewName = viewHelper.newDefaultTextView(activity);
		viewName.setText(calendarBean.getDiscription());
		viewText = viewHelper.newDefaultTextView(activity);
		viewText.setLayoutParams(viewHelper.getLinearLayoutParamMatch());
		if (calendarBean.getValue() == null
				|| "".equals(calendarBean.getValue())) {
			viewText.setText("Choose Date...");
		} else {
			viewText.setText(calendarBean.getValue());
		}
		viewText.setOnClickListener(new OnClickCalendarListener(activity,
				viewText));
		viewText.addTextChangedListener(new CalendarWatcher(calendarBean,
				formBean));
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

	public static class CalendarWatcher extends AbstractTextWatcher {

		private final CalendarBean calendarBean;

		private final FormBean formBean;

		public CalendarWatcher(CalendarBean calendarBean, FormBean formBean) {
			this.calendarBean = calendarBean;
			this.formBean = formBean;
		}

		@Override
		public void afterTextChanged(Editable editable) {
			Log.i("FormYourNotes",
					"set value of " + calendarBean.getDiscription() + " to '"
							+ editable.toString() + "'");
			calendarBean.getData().setItemId(calendarBean);
			// TODO why do i have to set that hard-coded?
			formBean.setDataChange(true);
			// if
			// (formBean.possibleDataChange(calendarBean.getData().getValue(),
			// editable.toString())) {
			// Log.i(LogTag.FYN.getTag(),
			// "data changed from "
			// + calendarBean.getData().getValue() + " to "
			// + editable.toString());
			// }
			calendarBean.getData().setValue(editable.toString());
		}
	}
}