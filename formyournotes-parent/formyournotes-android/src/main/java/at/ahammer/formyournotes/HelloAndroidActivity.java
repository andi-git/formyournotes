package at.ahammer.formyournotes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public class HelloAndroidActivity extends Activity {

	// private BeanViewMapper beanViewMapper = new BeanViewMapper();

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LogTag.FYN.getTag(), "onCreate");
		setContentView(R.layout.main);

		// LinearLayout layout = (LinearLayout)
		// findViewById(R.id.formyournotelayout);
		//
		// MyR myR = new MyR();
		// myR.getDrawable().setBorderTopElement(R.drawable.border_top_element);
		//
		// HebammenFormular hebammenFormular = new HebammenFormular();
		// for (FormYourNotesBean<?> formYourNotesBean :
		// hebammenFormular.getForm().getAllTopLevelItemsSortedByRank()) {
		// layout.addView(beanViewMapper.getView(this, myR,
		// hebammenFormular.getForm(), formYourNotesBean));
		// }

		// List<FormYourNotesBean> formYourNotesBeans = new
		// ArrayList<FormYourNotesBean>();
		// formYourNotesBeans.add(new ContactBean(0, 0, "Benutzer", "Andreas",
		// "Ahammer"));
		// formYourNotesBeans.add(new EditTextBean(1, 1, "Info 1",
		// "ein bisschen Text"));
		// formYourNotesBeans.add(new EditTextBean(2, 2, "Info 2",
		// "noch mehr Text"));
		// CheckBoxGroupBean checkBoxGroupBean = new CheckBoxGroupBean(3, 3,
		// "ein paar check-boxes");
		// checkBoxGroupBean.getCheckBoxes().add(
		// new CheckBoxBean(4, 4, "box1", true));
		// checkBoxGroupBean.getCheckBoxes().add(
		// new CheckBoxBean(5, 5, "box2", true));
		// checkBoxGroupBean.getCheckBoxes().add(
		// new CheckBoxBean(5, 5, "box3", false));
		// formYourNotesBeans.add(checkBoxGroupBean);
		//
		// for (FormYourNotesBean formYourNotesBean : formYourNotesBeans) {
		// layout.addView(beanViewMapper.getView(this, formYourNotesBean));
		// }

		// Button button = new Button(this);
		// button.setText("Speichern");
		// button.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Log.i(LogTag.FYN.getTag(), "click button");
		// }
		//
		// });
		// Log.i(LogTag.FYN.getTag(), "my message");
		// layout.addView(button);

		// ContactDao contactDao = new ContactDao();
		// List<Contact> allContacts = contactDao.getAllContacts(this);
		// for (Contact contact : allContacts) {
		// Log.i("FormYourNotes", contact.toString());
		// }
		// for (String name : contactDao.getAllDisplayNames(this)) {
		// Log.i("FormYourNotes", name);
		// }

		// Cursor phones = getContentResolver().query(
		// ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
		// ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
		// new String[] { "61" }, null);
		// phones.moveToNext();
		// String displayName = phones
		// .getString(phones
		// .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		// Log.i("FormYourNotes", "contact id 61: " + displayName);

		// this.setContentView(R.layout.list_layout);
		// listView = (ListView) findViewById(R.id.my_list);
		// dataSource = new ArrayList<MyData>();
		// adapter = new MyListAdapter(this, R.layout.item_layout, dataSource);
		// listView.setAdapter(adapter);

		// Form form = new Form();
		// form.setId(1);
		// form.setName("formName");
		// Group group = new Group();
		// group.setId(2);
		// group.setName("groupName");
		// form.getGroups().add(group);

		// try {
		// openFileInput("");
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
	}
}
