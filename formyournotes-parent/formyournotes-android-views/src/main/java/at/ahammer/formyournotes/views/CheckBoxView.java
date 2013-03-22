package at.ahammer.formyournotes.views;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.FormBean;

public class CheckBoxView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final CheckBox checkBox;

	public CheckBoxView(Context context, MyR r, FormBean formBean,
			CheckBoxBean checkBoxBean) {
		super(context);
		setOrientation(HORIZONTAL);
		checkBox = viewHelper.newDefaultCheckBox(context);
		checkBox.setText(checkBoxBean.getDiscription());
		checkBox.setChecked(checkBoxBean.isChecked());
		checkBox.setOnCheckedChangeListener(new CheckBoxWatcher(checkBoxBean));
		addView(checkBox, viewHelper.getLinearLayoutParam());
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

	public static class CheckBoxWatcher implements
			CompoundButton.OnCheckedChangeListener {

		private final CheckBoxBean checkBoxBean;

		public CheckBoxWatcher(CheckBoxBean checkBoxBean) {
			this.checkBoxBean = checkBoxBean;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			Log.i("FormYourNotes",
					"set value of " + checkBoxBean.getDiscription() + " to '"
							+ isChecked + "'");
			checkBoxBean.getData().setChecked(isChecked);
		}
	}
}