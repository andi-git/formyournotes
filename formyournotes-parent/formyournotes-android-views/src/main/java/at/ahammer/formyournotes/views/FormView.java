package at.ahammer.formyournotes.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.CheckBoxGroupBean;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.EditTextBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;
import at.ahammer.formyournotes.beans.GroupBean;

public class FormView {

	private final List<View> topLevelElements = new ArrayList<View>();

	private final FormBean formBean;

	public FormView(FormBean formBean, Context context, FormR formR) {
		this.formBean = formBean;
		for (FormYourNotesBean<?> topLevelElement : formBean
				.getAllTopLevelItemsSortedByRank()) {
			topLevelElements.add(getView(formBean, context, formR, topLevelElement));
		}
	}

	public void addToView(LinearLayout layout) {
		for (View topLevelElement : topLevelElements) {
			layout.addView(topLevelElement);
		}
	}

	public static View getView(FormBean formBean, Context context, FormR formR, FormYourNotesBean<?> currentBean) {
		if (currentBean instanceof EditTextBean) {
			return new EditTextView(context, formR, formBean,
					(EditTextBean) currentBean);
		} else if (currentBean instanceof CheckBoxGroupBean) {
			return new CheckBoxGroupView(context, formR, formBean,
					(CheckBoxGroupBean) currentBean);
		} else if (currentBean instanceof CheckBoxBean) {
			return new CheckBoxView(context, formR, formBean,
					(CheckBoxBean) currentBean);
		} else if (currentBean instanceof ContactBean) {
			return new ContactView(context, formR, formBean,
					(ContactBean) currentBean);
		} else if (currentBean instanceof GroupBean) {
			return new GroupView(context, formR, formBean,
					(GroupBean) currentBean);
		}
		throw new RuntimeException("View for " + currentBean.getClass()
				+ " is not registered!");
	}

	public FormBean getFormBean() {
		return formBean;
	}
}