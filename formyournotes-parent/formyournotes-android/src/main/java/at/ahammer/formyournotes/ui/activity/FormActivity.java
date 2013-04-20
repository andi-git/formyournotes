package at.ahammer.formyournotes.ui.activity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import at.ahammer.formyournotes.R;
import at.ahammer.formyournotes.actionbar.ActionBarActivity;
import at.ahammer.formyournotes.ui.dialog.AddItemDialog;
import at.ahammer.formyournotes.ui.dialog.DeleteItemDialog;
import at.ahammer.formyournotes.ui.dialog.EditAccountDialog;
import at.ahammer.formyournotes.ui.dialog.EditItemDialog;
import at.ahammer.formyournotes.util.FYNActionBarHelper;
import at.ahammer.formyournotes.util.FYNViewHelper;

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
			FYNViewHelper.INSTANCE.saveCurrentForm(this);
			break;
		case R.id.menu_sync:
			Toast.makeText(this, "Synchronize", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_add_item:
			new AddItemDialog(this).show();
			break;
		case R.id.menu_edit_item:
			new EditItemDialog(this).show();
			break;
		case R.id.menu_delete_item:
			new DeleteItemDialog(this).show();
			break;
		case R.id.menu_edit_account:
			new EditAccountDialog(this).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
