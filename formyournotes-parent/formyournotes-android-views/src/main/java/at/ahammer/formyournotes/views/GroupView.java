package at.ahammer.formyournotes.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;
import at.ahammer.formyournotes.beans.GroupBean;

public class GroupView extends LinearLayout {

	private BeanViewMapper beanViewMapper = new BeanViewMapper();
	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;

	public GroupView(Context context, MyR r, FormBean formBean,
			GroupBean groupBean) {
		super(context);
		setOrientation(VERTICAL);
		setBackground(getResources().getDrawable(
				r.getDrawable().getBorderTopElement()));
		viewName = viewHelper.newHeaderTextView(context);
		viewName.setText(groupBean.getName());
		addView(viewName, viewHelper.getLinearLayoutParamFirstInRow());
		for (FormYourNotesBean<?> child : formBean.getAllChildren(groupBean)) {
			View view = beanViewMapper
					.getView(getContext(), r, formBean, child);
			addView(view, viewHelper.getLinearLayoutParamFirstInRow());
		}
	}

	public String getName() {
		return viewName.getText().toString();
	}
}