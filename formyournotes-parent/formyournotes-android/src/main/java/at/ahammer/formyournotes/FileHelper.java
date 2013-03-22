package at.ahammer.formyournotes;

import java.io.File;

import android.content.Context;
import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public class FileHelper {

	private final File externalStorage;

	public FileHelper(Context context) {
		externalStorage = new File(context.getExternalFilesDir(null), "fyn");
		Log.i(LogTag.FYN.getTag(), "external storage is: " + externalStorage);
		if (!externalStorage.exists()) {
			Log.i(LogTag.FYN.getTag(), "external storage doesn't exist");
			externalStorage.mkdirs();
			Log.i(LogTag.FYN.getTag(), "external storage created");
		}
	}

	public File getStorage() {
		return externalStorage;
	}
}
