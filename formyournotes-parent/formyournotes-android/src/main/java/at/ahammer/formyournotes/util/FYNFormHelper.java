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
		formR.getDrawable().setButtonContact(R.drawable.button_contact);
		formR.getDrawable().setButtonChat(R.drawable.button_chat);
		formR.getDrawable().setButtonCalendar(R.drawable.button_calendar);
		return formR;
	}
}
