package at.ahammer.formyournotes.beans;

import java.util.Arrays;

import at.ahammer.formyournotes.beans.GroupBean.Border;
import at.ahammer.formyournotes.beans.GroupBean.Orientation;
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
		GroupBean groupBesucheDavor = form.newGroupBean(10, 10, 0, "Besuche",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupGeburtsverlauf = form.newGroupBean(11, 11, 0,
				"Geburtsverlauf", Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupGeburt = form.newGroupBean(12, 12, 0,
				"Geburt", Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupKind = form.newGroupBean(13, 13, 0, "Kind",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupWochenbettverlauf = form.newGroupBean(14, 14, 0, "",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupBesucheDanach = form.newGroupBean(15, 15, 0, "Besuche",
				Orientation.VERTICAL, Border.TOP_ELEMENT);
		GroupBean groupPostPartum = form.newGroupBean(16, 16, 0, "Post Partum",
				Orientation.VERTICAL, Border.TOP_ELEMENT);

		// elements for group mutter
		form.newContactBean(100, 1, groupMutter, "Mutter");
		form.newEditText(101, 2, groupMutter, "");
		form.newCalendar(102, 3, groupMutter, "Geburtsdatum",
				CalendarBean.Type.DATE, false);
		form.newEditText(103, 4, groupMutter, "Versicherungsnummer");
		form.newEditText(104, 5, groupMutter, "Krankenkasse");
		form.newCheckBoxBean(105, 6, groupMutter, "Zusatzversicherung");
		form.newEditText(106, 7, groupMutter, "Beruf");

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
		form.newSelect(160, 1, groupLaborbefunde, "BG",
				Arrays.asList("", "A", "B", "0", "AB"));
		form.newSelect(161, 2, groupLaborbefunde, "Rh",
				Arrays.asList("", "pos", "neg"));
		form.newSelect(162, 3, groupLaborbefunde, "HbsAG",
				Arrays.asList("", "pos", "neg"));
		form.newSelect(163, 4, groupLaborbefunde, "HIV",
				Arrays.asList("", "pos", "neg"));
		form.newSelect(164, 5, groupLaborbefunde, "TOXO",
				Arrays.asList("", "pos", "neg"));
		form.newSelect(165, 6, groupLaborbefunde, "RÖ-AK",
				Arrays.asList("", "pos", "neg"));
		form.newEditText(166, 7, groupLaborbefunde, "ERY");
		form.newEditText(167, 8, groupLaborbefunde, "HB");
		form.newEditText(168, 9, groupLaborbefunde, "OGTT");
		form.newEditText(169, 10, groupLaborbefunde, "Organscreening");
		form.newEditText(170, 11, groupLaborbefunde, "Sonstiges");

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
		form.newEditText(193, 4, groupAnamnese, "Sonstiges");
		form.newEditText(194, 5, groupAnamnese, "Eigene Geburt");
		form.newEditText(195, 6, groupAnamnese, "Wünsche");
		form.newEditText(196, 7, groupAnamnese, "Wunschkind");

		// elements for group Geburtsvorbereitung
		createCheckboxAndEditText(form, groupGeburtsvorbereitung, "AK", 1, 300);
		createCheckboxAndEditText(form, groupGeburtsvorbereitung, "Epi-No", 2,
				310);
		createCheckboxAndEditText(form, groupGeburtsvorbereitung, "GV", 3, 320);
		createCheckboxAndEditText(form, groupGeburtsvorbereitung, "Sectio", 4,
				330);
		createCheckboxAndEditText(form, groupGeburtsvorbereitung, "Analgesie",
				5, 340);
		createCheckboxAndEditText(form, groupGeburtsvorbereitung, "Revers", 6,
				350);

		// elements for group Besuche davor
		createGroupBesuche(form, groupBesucheDavor, "1. Besuch", 1, 600, "SSW");
		createGroupBesuche(form, groupBesucheDavor, "2. Besuch", 2, 610, "SSW");
		createGroupBesuche(form, groupBesucheDavor, "3. Besuch", 3, 620, "SSW");
		createGroupBesuche(form, groupBesucheDavor, "4. Besuch", 4, 630, "SSW");
		createGroupBesuche(form, groupBesucheDavor, "5. Besuch", 5, 640, "SSW");
		createGroupBesuche(form, groupBesucheDavor, "6. Besuch", 6, 650, "SSW");

		// elements for group Besuche danach
		createGroupBesuche(form, groupBesucheDanach, "1. Besuch", 1, 700,
				"Tag pp");
		createGroupBesuche(form, groupBesucheDanach, "2. Besuch", 2, 710,
				"Tag pp");
		createGroupBesuche(form, groupBesucheDanach, "3. Besuch", 3, 720,
				"Tag pp");
		createGroupBesuche(form, groupBesucheDanach, "4. Besuch", 4, 730,
				"Tag pp");
		createGroupBesuche(form, groupBesucheDanach, "5. Besuch", 5, 740,
				"Tag pp");
		createGroupBesuche(form, groupBesucheDanach, "6. Besuch", 6, 750,
				"Tag pp");

		// elements for group Post Partum
		createCheckboxAndEditText(form, groupPostPartum, "Rhes.", 1, 400);
		createCheckboxAndEditText(form, groupPostPartum, "PKU", 1, 410);
		createCheckboxAndEditText(form, groupPostPartum, "Konakion", 1, 420);
		createCheckboxAndEditText(form, groupPostPartum, "Rö", 1, 430);
		createCheckboxAndEditText(form, groupPostPartum, "KIA", 1, 440);

		// elements for group Geburtsverlauf
		form.newEditText(280, 1, groupGeburtsverlauf, "");

		// elements for group Geburts
		form.newEvent(230, 1, groupGeburt, "Geburt", 250, 251);
		form.newEditText(232, 3, groupGeburt, "Geburt in");
		form.newEditText(233, 4, groupGeburt, "Geburt aus");
		form.newSelect(234, 5, groupGeburt, "Geburtsmodus", Arrays
				.asList("", "spontan", "Kiwi", "Forceps", "prim. Sectio",
						"sec. Sectio"));
		form.newEditText(235, 6, groupGeburt, "Geburtsposition");
		form.newSelect(236, 7, groupGeburt, "Geburtsverletzung", Arrays
				.asList("", "keine", "DR I", "DR II", "DR III", "DR IV",
						"Labienriss", "Cervixriss", "Scheidenriss"));
		form.newCheckBoxBean(237, 8, groupGeburt, "Nubain");
		form.newCheckBoxBean(238, 9, groupGeburt, "Epidurale");
		form.newCheckBoxBean(239, 10, groupGeburt, "Syntocinon");
		form.newCheckBoxBean(240, 11, groupGeburt, "Bachblüten");
		form.newCheckBoxBean(241, 12, groupGeburt, "Homöopathie");
		form.newCheckBoxBean(242, 13, groupGeburt, "Akupunktur");
		form.newCheckBoxBean(243, 14, groupGeburt, "Epi No");
		form.newEditText(244, 15, groupGeburt, "Einleitung wegen");
		form.newEditText(245, 16, groupGeburt, "Einleitung mit");
		form.newEditText(246, 17, groupGeburt, "BSP");
		form.newEditText(247, 18, groupGeburt, "FW");
		form.newEditText(248, 19, groupGeburt, "Plazenta");
		form.newEditText(249, 20, groupGeburt, "Geburtsdauer");
		form.newEditText(259, 21, groupGeburt, "Arzt");

		// elements for group Kind
		form.newSelect(260, 1, groupKind, "Geschlecht",
				Arrays.asList("", "m", "w"));
		form.newEditText(261, 2, groupKind, "Name");
		form.newEditText(262, 3, groupKind, "Apgar");
		form.newEditText(263, 4, groupKind, "NspH - BE");
		form.newEditText(264, 5, groupKind, "Gewicht");
		form.newEditText(265, 6, groupKind, "Länge");
		form.newEditText(266, 7, groupKind, "Kopf");

		// elements for group Wochenbettverlauf
		form.newEditText(270, 1, groupWochenbettverlauf, "Komplikationen");
		form.newEditText(271, 2, groupWochenbettverlauf, "Entlassung");

		return form;
	}

	private void createCheckboxAndEditText(FormBean form,
			GroupBean groupBesuche, String header, int rank, int startId) {
		GroupBean groupGeburtsvorbereitung1 = form.newGroupBean(startId++,
				rank, groupBesuche, "", Orientation.HORIZONTAL, Border.NONE);
		form.newCheckBoxBean(startId++, 1, groupGeburtsvorbereitung1, header);
		form.newEditText(startId++, 2, groupGeburtsvorbereitung1, "");
	}

	private void createGroupBesuche(FormBean form, GroupBean groupBesuche,
			String header, int rank, int startId, String bezeichnung) {
		GroupBean besuch = form.newGroupBean(startId++, rank, groupBesuche,
				header, Orientation.VERTICAL, Border.NONE);
		GroupBean row1 = form.newGroupBean(startId++, 1, besuch, null,
				Orientation.HORIZONTAL, Border.NONE);
		GroupBean row2 = form.newGroupBean(startId++, 2, besuch, null,
				Orientation.HORIZONTAL, Border.NONE);
		form.newEvent(startId++, 1, row1, "Besuch", startId++, startId++);
		form.newEditText(startId++, 2, row2, bezeichnung);
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
		data.add(new CalendarData("", 102));
		data.add(new EditTextData("0000010180", 103));
		data.add(new EditTextData("WGKK", 104));
		data.add(new CheckBoxData(false, 105));
		data.add(new CalendarData("02.03.2013", 250));
		data.add(new CalendarData("21:45", 251));
		data.add(new CalendarData("01.02.2013", 604));
		data.add(new CalendarData("08:00", 605));
		return data;
	}

	private FormData createData2() {
		FormData data = new FormData(1, 2, "Petra Ahammer");
		data.add(new ContactData("Petra Ahammer", 100));
		data.add(new CalendarData("31.10.1978", 102));
		data.add(new EditTextData("0000311078", 103));
		data.add(new EditTextData("KFA", 104));
		data.add(new CheckBoxData(true, 105));
		data.add(new EditTextData("Hebamme", 106));
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
