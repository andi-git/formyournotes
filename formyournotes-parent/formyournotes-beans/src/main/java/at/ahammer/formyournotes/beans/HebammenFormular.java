package at.ahammer.formyournotes.beans;

import at.ahammer.formyournotes.beans.GroupBean.Border;
import at.ahammer.formyournotes.beans.GroupBean.Orientation;
import at.ahammer.formyournotes.data.CalendarData;
import at.ahammer.formyournotes.data.CheckBoxData;
import at.ahammer.formyournotes.data.ContactData;
import at.ahammer.formyournotes.data.EditTextData;
import at.ahammer.formyournotes.data.EventData;
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
				"Personendaten Mutter", Orientation.VERTICAL,
				Border.TOP_ELEMENT);
		GroupBean groupPartner = form.newGroupBean(2, 2, 0,
				"Personendaten Partner", Orientation.VERTICAL,
				Border.TOP_ELEMENT);
		// GroupBean groupGeburtstermin= form.newGroupBean(3, 3, 0,
		// "Geburtstermin");
		GroupBean groupAktuelleSchwangerschaft = form.newGroupBean(4, 4, 0,
				"aktuelle Schwangerschaft", Orientation.VERTICAL,
				Border.TOP_ELEMENT);
		GroupBean groupBisherigeGeburten = form.newGroupBean(5, 5, 0,
				"bisherige Geburten", Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupLaborbefunde = form.newGroupBean(6, 6, 0,
				"Laborbefunde", Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupMedizinischeBetreuung = form.newGroupBean(7, 7, 0,
				"medizinische Betreuung", Orientation.VERTICAL,
				Border.TOP_ELEMENT);
		GroupBean groupAnamnese = form.newGroupBean(8, 8, 0, "Anamnese",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupGeburtsvorbereitung = form
				.newGroupBean(9, 9, 0, "Geburtsvorbereitung",
						Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupBesuche = form.newGroupBean(10, 10, 0, "Besuche",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupPostPertum = form.newGroupBean(11, 11, 0, "Post Pertum",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupGeburtsverlauf = form.newGroupBean(12, 12, 0,
				"Geburtsverlauf", Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupKind = form.newGroupBean(13, 13, 0, "Kind",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupWochenbettverlauf = form.newGroupBean(14, 14, 0,
				"Wochenbettverlauf", Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupXXX = form.newGroupBean(15, 15, 0, "",
				Orientation.VERTICAL, Border.TOP_ELEMENT);

		// elements for group mutter
		form.newContactBean(100, 1, groupMutter, "Mutter");
		form.newCalendar(101, 2, groupMutter, "Geburtsdatum",
				CalendarBean.Type.DATE, false);
		form.newEditText(102, 3, groupMutter, "Versicherungsnummer");
		form.newEditText(103, 4, groupMutter, "Krankenkasse");
		form.newCheckBoxBean(104, 5, groupMutter, "Zusatzversicherung");
		form.newEditText(105, 6, groupMutter, "Beruf");

		// elements for group partner
		form.newContactBean(110, 1, groupPartner, "Partner");
		form.newCalendar(111, 2, groupPartner, "Geburtsdatum",
				CalendarBean.Type.DATE, false);
		form.newEditText(112, 3, groupPartner, "Beruf");

		// elements for group geburtstermin
		// form.newCalendar(120, 1, groupGeburtstermin, "Errechneter Termin",
		// CalendarBean.Type.DATE);
		// form.newEditText(121, 2, groupGeburtstermin, "Partus");
		// form.newEditText(122, 3, groupGeburtstermin, "Geburtsort");
		// form.newEditText(123, 4, groupGeburtstermin, "Arzt");
		// form.newEditText(124, 5, groupGeburtstermin, "Besonderheit");

		// elements for group aktuelle Schwangerschaft
		form.newCalendar(130, 1, groupAktuelleSchwangerschaft,
				"Errechneter Termin", CalendarBean.Type.DATE, true);
		form.newEditText(132, 3, groupAktuelleSchwangerschaft, "Geburtsort");
		form.newEditText(133, 4, groupAktuelleSchwangerschaft, "Arzt");
		form.newEditText(134, 5, groupAktuelleSchwangerschaft, "Besonderheit");
		form.newEditText(135, 6, groupAktuelleSchwangerschaft, "LNR");
		form.newEditText(136, 7, groupAktuelleSchwangerschaft, "Konzeption");
		form.newEditText(137, 8, groupAktuelleSchwangerschaft, "Zyklus");
		form.newEditText(138, 9, groupAktuelleSchwangerschaft, "Partus");
		form.newEditText(139, 10, groupAktuelleSchwangerschaft, "Frühere SS");

		// elements for group bisherige Geburten
		createGroupGeburt(form, groupBisherigeGeburten, "1. Geburt", 1, 500);
		createGroupGeburt(form, groupBisherigeGeburten, "2. Geburt", 2, 520);
		createGroupGeburt(form, groupBisherigeGeburten, "3. Geburt", 3, 540);
		createGroupGeburt(form, groupBisherigeGeburten, "4. Geburt", 4, 560);

		// elements for group Laborbefunde
		form.newEditText(160, 1, groupLaborbefunde, "BG");
		form.newEditText(161, 2, groupLaborbefunde, "Rh");
		form.newEditText(162, 3, groupLaborbefunde, "HbsAG");
		form.newEditText(163, 4, groupLaborbefunde, "HIV");
		form.newEditText(164, 5, groupLaborbefunde, "TOXO");
		form.newEditText(165, 6, groupLaborbefunde, "RÖ-AK");
		form.newEditText(166, 7, groupLaborbefunde, "ERY");
		form.newEditText(167, 8, groupLaborbefunde, "HB");
		form.newEditText(168, 9, groupLaborbefunde, "OGTT");
		form.newEditText(169, 10, groupLaborbefunde, "Organscreening");

		// elements for group medizinische Betreuung
		form.newEditText(180, 1, groupMedizinischeBetreuung, "Facharzt");
		form.newEditText(181, 2, groupMedizinischeBetreuung,
				"Kontaktaufnahme durch");
		form.newEditText(182, 3, groupMedizinischeBetreuung,
				"Geburtsvorbereitung");

		// elements for group Anamnese
		form.newEditText(190, 1, groupAnamnese, "Allergie");
		form.newEditText(191, 2, groupAnamnese, "Psych.");
		form.newEditText(192, 3, groupAnamnese, "Erkrankungen, OP");
		form.newEditText(193, 4, groupAnamnese, "Eigene Geburt");
		form.newEditText(194, 5, groupAnamnese, "Wünsche");
		form.newEditText(195, 6, groupAnamnese, "Wunschkind");

		// elements for group Geburtsvorbereitung
		form.newCheckBoxBean(200, 1, groupGeburtsvorbereitung, "AK");
		form.newCheckBoxBean(201, 2, groupGeburtsvorbereitung, "Epi-No");
		form.newCheckBoxBean(202, 3, groupGeburtsvorbereitung, "GV");
		form.newCheckBoxBean(203, 4, groupGeburtsvorbereitung, "Sectio");
		form.newCheckBoxBean(204, 5, groupGeburtsvorbereitung, "Analgesie");
		form.newCheckBoxBean(205, 6, groupGeburtsvorbereitung, "Revers");

		// elements for group Besuche
		createGroupBesuche(form, groupBesuche, "1. Besuch", 1, 600);
		createGroupBesuche(form, groupBesuche, "2. Besuch", 2, 610);
		createGroupBesuche(form, groupBesuche, "3. Besuch", 3, 620);
		createGroupBesuche(form, groupBesuche, "4. Besuch", 4, 630);
		createGroupBesuche(form, groupBesuche, "5. Besuch", 5, 640);
		createGroupBesuche(form, groupBesuche, "6. Besuch", 6, 650);
		createGroupBesuche(form, groupBesuche, "7. Besuch", 7, 660);
		createGroupBesuche(form, groupBesuche, "8. Besuch", 8, 670);
		createGroupBesuche(form, groupBesuche, "9. Besuch", 9, 680);
		createGroupBesuche(form, groupBesuche, "10. Besuch", 10, 690);
		createGroupBesuche(form, groupBesuche, "11. Besuch", 11, 700);
		createGroupBesuche(form, groupBesuche, "12. Besuch", 12, 710);

		// elements for group Post Pertum
		form.newCheckBoxBean(220, 1, groupPostPertum, "Rhes.");
		form.newCheckBoxBean(221, 2, groupPostPertum, "PKU");
		form.newCheckBoxBean(222, 3, groupPostPertum, "Konakion");
		form.newCheckBoxBean(223, 4, groupPostPertum, "Rö");
		form.newCheckBoxBean(224, 5, groupPostPertum, "KIA");

		// elements for group Geburtsverlauf
		CalendarBean geburtAm = form.newCalendar(250, 1, null, "am",
				CalendarBean.Type.DATE, false);
		CalendarBean geburtUm = form.newCalendar(251, 2, null, "um",
				CalendarBean.Type.TIME, false);
		form.newEvent(230, 1, groupGeburtsverlauf, "Geburt", geburtAm, geburtUm);
		form.newEditText(232, 3, groupGeburtsverlauf, "Geburt in");
		form.newEditText(233, 4, groupGeburtsverlauf, "Geburt aus");
		form.newEditText(234, 5, groupGeburtsverlauf, "Geburtsmodus");
		form.newEditText(235, 6, groupGeburtsverlauf, "Geburtsposition");
		form.newEditText(236, 7, groupGeburtsverlauf, "Geburtsverletzung");
		form.newEditText(237, 8, groupGeburtsverlauf, "Nubain");
		form.newEditText(238, 9, groupGeburtsverlauf, "Epidurale");
		form.newEditText(239, 10, groupGeburtsverlauf, "Syntocinon");
		form.newEditText(240, 11, groupGeburtsverlauf, "Bachblüten");
		form.newEditText(241, 12, groupGeburtsverlauf, "Homöopathie");
		form.newEditText(242, 13, groupGeburtsverlauf, "Akupunktur");
		form.newEditText(243, 14, groupGeburtsverlauf, "Epi No");
		form.newEditText(244, 15, groupGeburtsverlauf, "Einleitung wegen");
		form.newEditText(245, 16, groupGeburtsverlauf, "Einleitung mit");
		form.newEditText(246, 17, groupGeburtsverlauf, "BSP");
		form.newEditText(247, 18, groupGeburtsverlauf, "FW");
		form.newEditText(248, 19, groupGeburtsverlauf, "Plazenta");
		form.newEditText(249, 20, groupGeburtsverlauf, "Geburtsdauer");

		// elements for group Kind
		form.newEditText(260, 1, groupKind, "Geschlecht");
		form.newEditText(261, 2, groupKind, "Name");
		form.newEditText(262, 3, groupKind, "Apgar");
		form.newEditText(263, 4, groupKind, "NspH - BE");
		form.newEditText(264, 5, groupKind, "Gewicht");
		form.newEditText(265, 6, groupKind, "Länge");
		form.newEditText(266, 7, groupKind, "Kopf");

		// elements for group Wochenbettverlauf
		form.newEditText(270, 1, groupWochenbettverlauf, "Komplikationen");
		form.newEditText(271, 2, groupWochenbettverlauf, "Entlassung");

		// elements for group ???
		form.newEditText(280, 1, groupXXX, "Hebamme");
		form.newEditText(281, 2, groupXXX, "Arzt");

		return form;
	}

	private void createGroupBesuche(FormBean form, GroupBean groupBesuche,
			String header, int rank, int startId) {
		GroupBean besuch = form.newGroupBean(startId++, rank, groupBesuche,
				header, Orientation.VERTICAL, Border.NONE);
		GroupBean row1 = form.newGroupBean(startId++, 1, besuch, null,
				Orientation.HORIZONTAL, Border.NONE);
		GroupBean row2 = form.newGroupBean(startId++, 2, besuch, null,
				Orientation.HORIZONTAL, Border.NONE);
		CalendarBean date = form.newCalendar(startId++, 1, null, "am",
				CalendarBean.Type.DATE, false);
		CalendarBean time = form.newCalendar(startId++, 2, null, "um",
				CalendarBean.Type.TIME, false);
		form.newEvent(startId++, 1, row1, "Besuch", date, time);
		form.newEditText(startId++, 2, row2, "SSW");
		form.newEditText(startId++, 3, row2, "Notiz");
	}

	private void createGroupGeburt(FormBean form,
			GroupBean groupBisherigeGeburten, String header, int rank,
			int startId) {
		GroupBean geburt = form.newGroupBean(startId++, rank,
				groupBisherigeGeburten, header, Orientation.VERTICAL,
				Border.NONE);
		GroupBean row1 = form.newGroupBean(startId++, 1, geburt, null,
				Orientation.HORIZONTAL, Border.NONE);
		GroupBean row2 = form.newGroupBean(startId++, 2, geburt, null,
				Orientation.HORIZONTAL, Border.NONE);
		GroupBean row3 = form.newGroupBean(startId++, 3, geburt, null,
				Orientation.HORIZONTAL, Border.NONE);
		form.newCalendar(startId++, 1, row1, "am", CalendarBean.Type.DATE,
				false);
		form.newEditText(startId++, 2, row1, "in");
		form.newEditText(startId++, 1, row2, "Geschlecht");
		form.newEditText(startId++, 2, row2, "Gewicht");
		form.newEditText(startId++, 1, row3, "Verlauf");
	}

	private FormData createData1() {
		FormData data = new FormData(1, 1, "Miriam Musterfrau");
		data.add(new ContactData("Miriam Musterfrau", 100));
		data.add(new CalendarData("", 101));
		data.add(new EditTextData("0000010180", 102));
		data.add(new EditTextData("WGKK", 103));
		data.add(new CheckBoxData(false, 104));
		data.add(new EventData(new CalendarData("02.03.2013", 250),
				new CalendarData("21:45", 251), 230));
		data.add(new EventData(new CalendarData("01.02.2013", 603),
				new CalendarData("08:00", 604), 605));
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
