package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.data.CalendarData;
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
		// form
		FormBean form = new FormBean(1, "Hebammen Formular");
		
		// groups
		GroupBean groupMutter = form.newGroupBean(1, 1, 0,
				"Personendaten Mutter");
		GroupBean groupPartner = form.newGroupBean(2, 2, 0,
				"Personendaten Partner");
		GroupBean groupGeburtstermin= form.newGroupBean(3, 3, 0,
				"Geburtstermin");
		
		// elements for group mutter
		form.newContactBean(100, 1, groupMutter, "Mutter");
		form.newCalendar(101, 2, groupMutter, "Geburtsdatum", CalendarBean.Type.DATE);
		form.newEditText(102, 3, groupMutter, "Versicherungsnummer");
		form.newEditText(103, 4, groupMutter, "Krankenkasse");
		form.newCheckBoxBean(104, 5, groupMutter, "Zusatzversicherung");
		form.newEditText(105, 6, groupMutter, "Beruf");

		// elements for group partner
		form.newContactBean(110, 1, groupPartner, "Partner");
		form.newCalendar(111, 2, groupPartner, "Geburtsdatum", CalendarBean.Type.DATE);
		form.newEditText(112, 3, groupPartner, "Beruf");

		// elements for group geburtstermin
		form.newCalendar(120, 1, groupGeburtstermin, "Errechneter Termin", CalendarBean.Type.DATE);
		form.newEditText(121, 2, groupGeburtstermin, "Partus");
		form.newEditText(122, 3, groupGeburtstermin, "Geburtsort");
		form.newEditText(123, 4, groupGeburtstermin, "Arzt");
		form.newEditText(124, 5, groupGeburtstermin, "Besonderheit");
		
		return form;
	}

	private FormData createData1() {
		FormData data = new FormData(1, 1, "Miriam Musterfrau");
		data.add(new ContactData("Miriam Musterfrau", 100));
		data.add(new CalendarData("", 101));
		data.add(new EditTextData("0000010180", 102));
		data.add(new EditTextData("WGKK", 103));
		data.add(new CheckBoxData(false, 104));
		return data;
	}

	private FormData createData2() {
		FormData data = new FormData(1, 2, "Petra Ahammer");
		data.add(new ContactData("Petra Ahammer", 100));
		data.add(new CalendarData("31.10.1978", 101));
		data.add(new EditTextData("0000311078", 102));
		data.add(new EditTextData("KFA", 103));
		data.add(new CheckBoxData(true, 104));
		data.add(new EditTextData("Hebamme", 105));
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
