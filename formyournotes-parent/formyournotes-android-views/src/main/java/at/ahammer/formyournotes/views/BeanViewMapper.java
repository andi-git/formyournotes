package at.ahammer.formyournotes.views;

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

public class BeanViewMapper {

	public void add(Context context, LinearLayout layout, MyR r, FormBean formBean) {
		for (FormYourNotesBean<?> formYourNotesBean : formBean.getAllTopLevelItemsSortedByRank()) {
			layout.addView(getView(context, r, formBean, formYourNotesBean));
		}
	}

	public View getView(Context context, MyR r, FormBean formBean,
			FormYourNotesBean<?> currentBean) {
		if (currentBean instanceof EditTextBean) {
			return new EditTextView(context, r, formBean,
					(EditTextBean) currentBean);
		} else if (currentBean instanceof CheckBoxGroupBean) {
			return new CheckBoxGroupView(context, r, formBean,
					(CheckBoxGroupBean) currentBean);
		} else if (currentBean instanceof CheckBoxBean) {
			return new CheckBoxView(context, r, formBean,
					(CheckBoxBean) currentBean);
		} else if (currentBean instanceof ContactBean) {
			return new ContactView(context, r, formBean,
					(ContactBean) currentBean);
		} else if (currentBean instanceof GroupBean) {
			return new GroupView(context, r, formBean, (GroupBean) currentBean);
		}
		throw new RuntimeException("View for " + currentBean.getClass()
				+ " is not registered!");
	}
}
