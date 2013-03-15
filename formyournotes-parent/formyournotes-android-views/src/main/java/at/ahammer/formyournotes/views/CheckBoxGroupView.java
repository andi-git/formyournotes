package at.ahammer.formyournotes.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.CheckBoxGroupBean;
import at.ahammer.formyournotes.beans.FormBean;

public class CheckBoxGroupView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;
	private final TextView viewColon;
	private final List<CheckBoxView> checkBoxes = new ArrayList<CheckBoxView>();
	
	public CheckBoxGroupView(Context context, MyR r, FormBean formBean, CheckBoxGroupBean checkBoxGroupBean) {
		super(context);
		setOrientation(VERTICAL);
		viewName = viewHelper.newDefaultTextView(context);
		viewName.setText(checkBoxGroupBean.getDiscription());
		viewColon = viewHelper.newDefaultTextView(context);
		viewColon.setText(": ");
		for (CheckBoxBean checkBoxBean : checkBoxGroupBean.getCheckBoxes()) {
			checkBoxes.add(new CheckBoxView(context, r, formBean, checkBoxBean));
		}
		addView(addRow(context, viewName, viewColon), new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
//		addView(viewName, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT));
//		addView(viewColon, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT));
		for (CheckBoxView checkBoxView : checkBoxes) {
			TextView viewBlank = viewHelper.newDefaultTextView(context);
			viewBlank.setText("    ");
			addView(addRow(context, viewBlank, checkBoxView), new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}
	
	private LinearLayout addRow(Context context, View... views) {
		LinearLayout row = new LinearLayout(context);
		row.setOrientation(LinearLayout.HORIZONTAL);
		for (View view : views) {
			row.addView(view);
		}
		return row;
	}
	
	public String getName() {
	     return viewName.getText().toString();
	}

	public List<CheckBoxView> getCheckBoxes() {
	     return checkBoxes;
	}

	public void setName(String name) {
	     viewName.setText(name);
	}
}