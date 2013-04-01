package at.ahammer.formyournotes.ui.activity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.actionbar.ActionBarActivity;
import at.ahammer.formyournotes.ui.dialog.AddItemDialog;
import at.ahammer.formyournotes.ui.dialog.DeleteItemDialog;
import at.ahammer.formyournotes.util.FYNActionBarHelper;
import at.ahammer.formyournotes.util.FormYourNotesController;

public class FormActivity extends ActionBarActivity {

	public FormActivity() {
		super(FYNActionBarHelper.INSTANCE.getActionBarR());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		// Calling super after populating the menu is necessary here to ensure
		// that the action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_save:
			Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
			FormYourNotesController.INSTANCE.updateFormData(this);
			break;
		case R.id.menu_add_item:
			AddItemDialog addItemDialog = new AddItemDialog(this);
			addItemDialog.show();
			break;
		case R.id.menu_delete_item:
			DeleteItemDialog deleteItemDialog = new DeleteItemDialog(this);
			deleteItemDialog.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
