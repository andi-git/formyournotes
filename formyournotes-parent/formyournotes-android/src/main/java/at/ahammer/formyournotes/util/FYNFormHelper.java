package at.ahammer.formyournotes.util;

import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.views.FormR;

public enum FYNFormHelper {

	INSTANCE;

	final FormR formR;

	FYNFormHelper() {
		formR = createFormR();
	}

	public FormR getFormR() {
		return formR;
	}

	public FormR createFormR() {
		FormR formR = new FormR();
		formR.getDrawable().setBorderTopElement(R.drawable.border_top_element);
		formR.getDrawable().setButtonEdit(R.drawable.button_edit);
		formR.getDrawable().setButtonDown(R.drawable.button_down);
		return formR;
	}
}
