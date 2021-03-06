package at.ahammer.formyournotes.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import at.ahammer.formyournotes.beans.CalendarBean;
import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.CheckBoxGroupBean;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.EditTextBean;
import at.ahammer.formyournotes.beans.EventBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;
import at.ahammer.formyournotes.beans.GroupBean;
import at.ahammer.formyournotes.beans.SelectBean;

public class FormView {

	private final List<View> topLevelElements = new ArrayList<View>();

	private final FormBean formBean;

	public FormView(FormBean formBean, Activity activity, FormR formR) {
		this.formBean = formBean;
		for (FormYourNotesBean<?> topLevelElement : formBean
				.getAllTopLevelItemsSortedByRank()) {
			topLevelElements.add(getView(formBean, activity, formR,
					topLevelElement));
		}
	}

	public List<View> getTopLevelElements() {
		return topLevelElements;
	}

	public static View getView(FormBean formBean, Activity activity,
			FormR formR, FormYourNotesBean<?> currentBean) {
		if (currentBean instanceof EditTextBean) {
			return new EditTextView(activity, formR, formBean,
					(EditTextBean) currentBean);
		} else if (currentBean instanceof CheckBoxGroupBean) {
			return new CheckBoxGroupView(activity, formR, formBean,
					(CheckBoxGroupBean) currentBean);
		} else if (currentBean instanceof CheckBoxBean) {
			return new CheckBoxView(activity, formR, formBean,
					(CheckBoxBean) currentBean);
		} else if (currentBean instanceof ContactBean) {
			return new ContactView(activity, formR, formBean,
					(ContactBean) currentBean);
		} else if (currentBean instanceof GroupBean) {
			return new GroupView(activity, formR, formBean,
					(GroupBean) currentBean);
		} else if (currentBean instanceof CalendarBean) {
			return new CalendarView(activity, formR, formBean,
					(CalendarBean) currentBean);
		} else if (currentBean instanceof EventBean) {
			return new EventView(activity, formR, formBean,
					(EventBean) currentBean);
		} else if (currentBean instanceof SelectBean) {
			return new SelectView(activity, formR, formBean,
					(SelectBean) currentBean);
		}
		throw new RuntimeException("View for " + currentBean.getClass()
				+ " is not registered!");
	}

	public FormBean getFormBean() {
		return formBean;
	}
}
