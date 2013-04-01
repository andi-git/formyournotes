package at.ahammer.formyournotes.views;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.ahammer.formyournotes.beans.EditTextBean;
import at.ahammer.formyournotes.beans.FormBean;

public class EditTextView extends LinearLayout {

	private ViewHelper viewHelper = new ViewHelper();
	private final TextView viewName;
	private final TextView viewColon;
	private final EditText viewText;

	public EditTextView(Context context, FormR r, FormBean formBean,
			EditTextBean editTextBean) {
		super(context);
		setOrientation(HORIZONTAL);
		viewName = viewHelper.newDefaultTextView(context);
		viewName.setText(editTextBean.getDiscription());
		viewText = viewHelper.newDefaultEditText(context);
		viewText.setText(editTextBean.getValue());
		viewText.addTextChangedListener(new EditTextWatcher(editTextBean));
		viewColon = viewHelper.newDefaultTextView(context);
		viewColon.setText(": ");
		addView(viewName, viewHelper.getLinearLayoutParamWrap());
		addView(viewColon, viewHelper.getLinearLayoutParamWrap());
		addView(viewText, viewHelper.getLinearLayoutParamWrap());
	}

	public String getName() {
		return viewName.getText().toString();
	}

	public String getText() {
		return viewText.getText().toString();
	}

	public void setName(String name) {
		viewName.setText(name);
	}

	public void setText(String text) {
		viewText.setText(text);
	}

	public static class EditTextWatcher extends AbstractTextWatcher {

		private final EditTextBean editTextBean;

		public EditTextWatcher(EditTextBean editTextBean) {
			this.editTextBean = editTextBean;
		}

		@Override
		public void afterTextChanged(Editable editable) {
			Log.i("FormYourNotes",
					"set value of " + editTextBean.getDiscription() + " to '"
							+ editable.toString() + "'");
			editTextBean.getData().setItemId(editTextBean);
			editTextBean.getData().setValue(editable.toString());
		}
	}
}