package at.ahammer.formyournotes.views;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.logging.LogTag;

public class CheckBoxView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final CheckBox checkBox;

	public CheckBoxView(Context context, FormR r, FormBean formBean,
			CheckBoxBean checkBoxBean) {
		super(context);
		setOrientation(HORIZONTAL);
		checkBox = viewHelper.newDefaultCheckBox(context);
		checkBox.setText(checkBoxBean.getDiscription());
		checkBox.setChecked(checkBoxBean.isChecked());
		checkBox.setOnCheckedChangeListener(new CheckBoxWatcher(checkBoxBean,
				formBean));
		addView(checkBox, viewHelper.getLinearLayoutParamWrap());
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

		private final FormBean formBean;

		public CheckBoxWatcher(CheckBoxBean checkBoxBean, FormBean formBean) {
			this.checkBoxBean = checkBoxBean;
			this.formBean = formBean;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			Log.i("FormYourNotes",
					"set value of " + checkBoxBean.getDiscription() + " to '"
							+ isChecked + "'");
			checkBoxBean.getData().setItemId(checkBoxBean);
			if (formBean.possibleDataChange(checkBoxBean.getData().isChecked(),
					isChecked)) {
				Log.i(LogTag.FYN.getTag(), "data changed from "
						+ checkBoxBean.getData().isChecked() + " to "
						+ isChecked);
			}

			checkBoxBean.getData().setChecked(isChecked);
		}
	}
}