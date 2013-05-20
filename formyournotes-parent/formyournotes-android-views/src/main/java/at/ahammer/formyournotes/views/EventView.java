package at.ahammer.formyournotes.views;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.CalendarBean;
import at.ahammer.formyournotes.beans.EventBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.calendar.CalendarIntent;
import at.ahammer.formyournotes.datetime.DateTimeHelper;

public class EventView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;
	private final ImageButton invokeButton;

	public EventView(final Activity activity, FormR r, final FormBean formBean,
			final EventBean eventBean) {
		super(activity);
		setId(eventBean.getId());
		setOrientation(HORIZONTAL);
		viewName = viewHelper.newDefaultTextView(activity);
		viewName.setText(eventBean.getDiscription());
		addView(viewName, viewHelper.getLinearLayoutCalendarParamWrap());
		addView(viewHelper.newDefaultTextViewBlank(activity), viewHelper.getLinearLayoutCalendarParamWrap());
		final CalendarBean date = formBean.getById(eventBean.getDate(), CalendarBean.class);
		addView(FormView.getView(formBean, activity, r, date),
				viewHelper.getLinearLayoutCalendarParamWrap());
		final CalendarBean time = formBean.getById(eventBean.getTime(), CalendarBean.class);
		addView(viewHelper.newDefaultTextViewBlank(activity), viewHelper.getLinearLayoutCalendarParamWrap());
		addView(FormView.getView(formBean, activity, r, time),
				viewHelper.getLinearLayoutCalendarParamWrap());
		invokeButton = new ImageButton(activity);
		invokeButton.setImageResource(r.getDrawable().getButtonCalendar());
		invokeButton.setBackground(null);
		invokeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				new CalendarIntent(activity)
						.//
						setBeginDate(
								DateTimeHelper.parseCalendar(date.getValue()
										+ " "
										+ time.getValue())).//
						setTitle(eventBean.getDiscription()).//
						perform();
			}
		});
		addView(invokeButton, viewHelper.getLinearLayoutParamWrap());
	}

	public String getName() {
		return viewName.getText().toString();
	}

	public void setName(String name) {
		viewName.setText(name);
	}
}