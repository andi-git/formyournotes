package at.ahammer.formyournotes.util;

import android.app.Activity;
import android.widget.Toast;
import at.ahammer.formyournotes.ui.activity.FormFragmentLayout.FormFragmentLayoutIntent;

public enum FYNSyncHelper {

	INSTANCE;

	public void performSync(Activity activity) {
		SyncTask syncTask = new SyncTask(activity);
		try {
			if (syncTask.execute().get()) {
				Toast.makeText(activity, "Sync completed!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, "General sync error!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(activity, "General sync error!", Toast.LENGTH_SHORT).show();
		}
		if (syncTask.isNewElement()) {
			activity.finish();
			activity.startActivity(new FormFragmentLayoutIntent(activity).//
					setMessage("here goes a message").//
					build());
		}
	}
}
