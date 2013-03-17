package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.beans.CheckBoxBean;
import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.EditTextBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beans.GroupBean;
import at.ahammer.formyournotes.data.CheckBoxData;
import at.ahammer.formyournotes.data.ContactData;
import at.ahammer.formyournotes.data.EditTextData;

public class HebammenFormular {

	private FormBean form;

	public HebammenFormular() {
		form = createForm();
	}

	private FormBean createForm() {
		FormBean form = new FormBean();
		form.setName("Hebammen Formular");
		GroupBean mutterGroup = form.newGroupBean(1, 1, 0,
				"Personendaten Mutter");
		ContactBean mutterContact = form.newContactBean(2, 2, mutterGroup,
				"Mutter");
		EditTextBean mutterGebDat = form.newEditText(3, 3, mutterGroup,
				"Geburtsdatum");
		EditTextBean mutterVsnr = form.newEditText(4, 4, mutterGroup,
				"Versicherungsnummer");
		EditTextBean mutterKK = form.newEditText(5, 5, mutterGroup,
				"Krankenkasse");
		CheckBoxBean mutterZusatzversicherung = form.newCheckBoxBean(6, 6,
				mutterGroup, "Zusatzversicherung");
		EditTextBean mutterBeruf = form.newEditText(7, 7, mutterGroup, "Beruf");

		ContactData dataMutterContact = new ContactData();
		dataMutterContact.setDisplayName("Petra Ahammer");
		mutterContact.setData(dataMutterContact);
		mutterGebDat.setData(new EditTextData("31.10.1978"));
		mutterVsnr.setData(new EditTextData("0000311078"));
		mutterKK.setData(new EditTextData("KFA"));
		mutterZusatzversicherung.setData(new CheckBoxData(true));
		mutterBeruf.setData(new EditTextData("Hebamme"));

		return form;
	}
	
	public FormBean getForm() {
		return form;
	}
}
