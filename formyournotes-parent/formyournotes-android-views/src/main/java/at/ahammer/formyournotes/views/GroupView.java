package at.ahammer.formyournotes.views;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;
import at.ahammer.formyournotes.beans.GroupBean;

public class GroupView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;

	public GroupView(Activity activity, FormR r, FormBean formBean,
			GroupBean groupBean) {
		super(activity);
		setId(groupBean.getId());
		setOrientation(VERTICAL);
		setBackground(getResources().getDrawable(
				r.getDrawable().getBorderTopElement()));
		setLayoutParams(viewHelper.getLinearLayoutParamMatch());
		viewName = viewHelper.newHeaderTextView(activity);
		viewName.setText(groupBean.getName());
		addView(viewName, viewHelper.getLinearLayoutParamFirstInRow());
		for (FormYourNotesBean<?> child : formBean.getAllChildren(groupBean)) {
			View view = FormView.getView(formBean, activity, r, child);
			addView(view, viewHelper.getLinearLayoutParamFirstInRow());
		}
	}

	public String getName() {
		return viewName.getText().toString();
	}
}