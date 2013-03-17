package at.ahammer.formyournotes.views;

import android.content.Context;
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

	public EditTextView(Context context, MyR r, FormBean formBean,
			EditTextBean editTextBean) {
		super(context);
		setOrientation(HORIZONTAL);
		viewName = viewHelper.newDefaultTextView(context);
		viewName.setText(editTextBean.getDiscription());
		viewText = viewHelper.newDefaultEditText(context);
		viewText.setText(editTextBean.getValue());
		viewColon = viewHelper.newDefaultTextView(context);
		viewColon.setText(": ");
		addView(viewName, viewHelper.getLinearLayoutParam());
		addView(viewColon, viewHelper.getLinearLayoutParam());
		addView(viewText, viewHelper.getLinearLayoutParam());
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
}