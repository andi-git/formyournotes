package at.ahammer.formyournotes.views;

import android.content.Context;
import android.view.View;
import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.CheckBoxGroupBean;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.EditTextBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;

public class BeanViewMapper {

	public View getView(Context context, FormYourNotesBean formYourNotesBean) {
		if (formYourNotesBean instanceof EditTextBean) {
			return new EditTextView(context, (EditTextBean) formYourNotesBean);
		} else if (formYourNotesBean instanceof CheckBoxGroupBean) {
			return new CheckBoxGroupView(context, (CheckBoxGroupBean) formYourNotesBean);
		} else if (formYourNotesBean instanceof CheckBoxBean) {
			return new CheckBoxView(context, (CheckBoxBean) formYourNotesBean);
		} else if (formYourNotesBean instanceof ContactBean) {
			return new ContactView(context, (ContactBean) formYourNotesBean);
		}
		throw new RuntimeException("View for " + formYourNotesBean.getClass()
				+ " is not registered!");
	}
}
