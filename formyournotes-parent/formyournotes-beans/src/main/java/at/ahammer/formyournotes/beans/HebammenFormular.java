package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.CheckBoxData;
import at.ahammer.formyournotes.data.ContactData;
import at.ahammer.formyournotes.data.EditTextData;
import at.ahammer.formyournotes.data.FormData;

public class HebammenFormular {

	private FormBean form;

	private FormData data1;

	private FormData data2;

	public HebammenFormular() {
		form = createForm();
		data1 = createData1();
		data2 = createData2();
	}

	private FormBean createForm() {
		FormBean form = new FormBean(1, "Hebammen Formular");
		GroupBean mutterGroup = form.newGroupBean(1, 1, 0,
				"Personendaten Mutter");
		form.newContactBean(2, 2, mutterGroup, "Mutter");
		form.newEditText(3, 3, mutterGroup, "Geburtsdatum");
		form.newEditText(4, 4, mutterGroup, "Versicherungsnummer");
		form.newEditText(5, 5, mutterGroup, "Krankenkasse");
		form.newCheckBoxBean(6, 6, mutterGroup, "Zusatzversicherung");
		form.newEditText(7, 7, mutterGroup, "Beruf");
		return form;
	}

	private FormData createData1() {
		FormData data = new FormData(1, 1, "Miriam Musterfrau");
		data.add(new ContactData("Miriam Musterfrau", 2));
		data.add(new EditTextData("01.01.1980", 3));
		data.add(new EditTextData("0000010180", 4));
		data.add(new EditTextData("WGKK", 5));
		data.add(new CheckBoxData(false, 6));
		data.add(new EditTextData("Angestellte", 7));
		return data;
	}

	private FormData createData2() {
		FormData data = new FormData(1, 2, "Petra Ahammer");
		data.add(new ContactData("Petra Ahammer", 2));
		data.add(new EditTextData("31.10.1978", 3));
		data.add(new EditTextData("0000311078", 4));
		data.add(new EditTextData("KFA", 5));
		data.add(new CheckBoxData(true, 6));
		data.add(new EditTextData("Hebamme", 7));
		return data;
	}

	public FormBean getForm() {
		return form;
	}

	public FormData getData1() {
		return data1;
	}

	public FormData getData2() {
		return data2;
	}
}
