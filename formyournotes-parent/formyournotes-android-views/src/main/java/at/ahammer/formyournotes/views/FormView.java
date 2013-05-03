package at.ahammer.formyournotes.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import at.ahammer.formyournotes.beans.CalendarBean;
import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.CheckBoxGroupBean;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.EditTextBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;
import at.ahammer.formyournotes.beans.GroupBean;
import at.ahammer.formyournotes.logging.LogTag;

public class FormView {

	private final List<View> topLevelElements = new ArrayList<View>();

	private final FormBean formBean;

	public FormView(FormBean formBean, Activity activity, FormR formR) {
		this.formBean = formBean;
		for (FormYourNotesBean<?> topLevelElement : formBean
				.getAllTopLevelItemsSortedByRank()) {
			Log.i(LogTag.FYN.getTag(), "add " + topLevelElement.getId()
					+ " to topLevelElement");
			topLevelElements.add(getView(formBean, activity, formR,
					topLevelElement));
		}
	}

	public List<View> getTopLevelElements() {
		return topLevelElements;
	}

	public static View getView(FormBean formBean, Activity activity,
			FormR formR, FormYourNotesBean<?> currentBean) {
		Log.i(LogTag.FYN.getTag(), "get view for: " + currentBean);
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
		}
		throw new RuntimeException("View for " + currentBean.getClass()
				+ " is not registered!");
	}

	public FormBean getFormBean() {
		return formBean;
	}
}
