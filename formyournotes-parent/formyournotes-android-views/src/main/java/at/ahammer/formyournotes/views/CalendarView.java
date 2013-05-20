package at.ahammer.formyournotes.views;

import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.CalendarBean;
import at.ahammer.formyournotes.beans.EventBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.calendar.CalendarIntent;
import at.ahammer.formyournotes.datetime.DateHelper;
import at.ahammer.formyournotes.datetime.OnDateClickListener;
import at.ahammer.formyournotes.datetime.OnTimeClickListener;

public class CalendarView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;
	private final TextView viewColon;
	private final TextView viewText;
	private final ImageButton invokeButton;

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
			viewText.setText(calendarBean.getDefaultValue());
		} else {
			viewText.setText(calendarBean.getValue());
		}
		if (calendarBean.getType() == CalendarBean.Type.TIME) {
			viewText.setOnClickListener(new OnTimeClickListener(activity,
					viewText));
		} else {
			viewText.setOnClickListener(new OnDateClickListener(activity,
					viewText));
		}
		viewText.addTextChangedListener(new CalendarWatcher(calendarBean,
				formBean));
		viewColon = viewHelper.newDefaultTextView(activity);
		viewColon.setText(": ");
		if (calendarBean.getDiscription() != null
				&& !"".equals(calendarBean.getDiscription())) {
			addView(viewName, getCalendarLayoutParams(formBean, calendarBean));
			addView(viewColon, getCalendarLayoutParams(formBean, calendarBean));
		}
		addView(viewText, getCalendarLayoutParams(formBean, calendarBean));
		invokeButton = new ImageButton(activity);
		if (calendarBean.getType() == CalendarBean.Type.DATE
				&& calendarBean.isShowInvoke()) {
			invokeButton.setImageResource(r.getDrawable().getButtonCalendar());
			invokeButton.setBackground(null);
			invokeButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					new CalendarIntent(activity)
							.//
							setBeginDate(
									DateHelper.parseCalendar(calendarBean
											.getValue())).//
							setTitle(calendarBean.getDiscription()).//
							setAllDay(true).//
							perform();
				}
			});
			addView(invokeButton, viewHelper.getLinearLayoutParamWrap());
		}
	}

	private LayoutParams getCalendarLayoutParams(final FormBean formBean, final CalendarBean calendarBean) {
		if (formBean.getById(calendarBean.getParent()) instanceof EventBean) {
			return viewHelper.getLinearLayoutParamWrap();
		}
		return viewHelper.getLinearLayoutCalendarParamWrap();
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