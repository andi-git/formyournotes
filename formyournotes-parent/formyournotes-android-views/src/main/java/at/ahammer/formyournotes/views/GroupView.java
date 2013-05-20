package at.ahammer.formyournotes.views;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.FormYourNotesBean;
import at.ahammer.formyournotes.beans.GroupBean;
import at.ahammer.formyournotes.beans.GroupBean.Orientation;

public class GroupView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;

	public GroupView(Activity activity, FormR r, FormBean formBean,
			GroupBean groupBean) {
		super(activity);
		setId(groupBean.getId());
		if (groupBean.getOrientation() == Orientation.VERTICAL) {
			setOrientation(VERTICAL);
		} else {
			setOrientation(HORIZONTAL);
		}
		if (groupBean.isTopLevelElement()) {
			setBackground(getResources().getDrawable(
					r.getDrawable().getBorderTopElement()));
		} else {
			switch (groupBean.getBorder()) {
			case NONE:
				// nothing
				break;
			case TOP_ELEMENT:
				setBackground(getResources().getDrawable(
						r.getDrawable().getBorderTopElement()));
				break;
			case LIGHT_GRAY:
				setBackground(getResources().getDrawable(
						r.getDrawable().getBorderLightGray()));
				break;
			default:
				break;
			}
		}
		setLayoutParams(viewHelper.getLinearLayoutParamMatch());
		viewName = viewHelper.newHeaderTextView(activity);
		viewName.setText(groupBean.getName());
		addView(viewName, viewHelper.getLinearLayoutGroupHeader());
		for (FormYourNotesBean<?> child : formBean.getAllChildren(groupBean)) {
			View view = FormView.getView(formBean, activity, r, child);
			addView(view, viewHelper.getLinearLayoutParamFirstInRow());
		}
	}

	public String getName() {
		return viewName.getText().toString();
	}
}