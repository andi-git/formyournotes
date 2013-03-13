package at.ahammer.formyournotes.views;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import at.ahammer.formyournotes.beans.CheckBoxBean;

public class CheckBoxView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final CheckBox checkBox;
	
	public CheckBoxView(Context context, CheckBoxBean checkBoxBean) {
		super(context);
		setOrientation(HORIZONTAL);
		checkBox = viewHelper.getDefaultCheckBox(context);
		checkBox.setText(checkBoxBean.getText());
		checkBox.setChecked(checkBoxBean.isChecked());
		addView(checkBox, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}
	
	public boolean isChecked() {
	     return checkBox.isChecked();
	}

	public String getText() {
	     return checkBox.getText().toString();
	}

	public void setChecked(boolean checked) {
	     checkBox.setChecked(checked);
	}

	public void setText(String text) {
	     checkBox.setText(text);
	}
}