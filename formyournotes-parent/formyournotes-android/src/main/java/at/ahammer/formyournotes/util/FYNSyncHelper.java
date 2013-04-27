package at.ahammer.formyournotes.util;

import android.content.Context;
import android.widget.Toast;

public enum FYNSyncHelper {

	INSTANCE;

	public void performSync(Context context) {
		SyncTask syncTask = new SyncTask(context);
		try {
			if (syncTask.execute().get()) {
				Toast.makeText(context, "Sync completed!", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(context, "General sync error!",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(context, "General sync error!", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
