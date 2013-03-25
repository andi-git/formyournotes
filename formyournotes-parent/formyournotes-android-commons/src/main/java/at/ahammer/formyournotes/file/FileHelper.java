package at.ahammer.formyournotes.file;

import java.io.File;

import android.content.Context;
import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public class FileHelper {

	private final File externalStorage;

	public FileHelper(Context context, String directory) {
		externalStorage = new File(context.getExternalFilesDir(null), directory);
		Log.i(LogTag.FYN.getTag(), "external storage is: " + externalStorage);
		if (!externalStorage.exists()) {
			Log.i(LogTag.FYN.getTag(), "external storage doesn't exist");
			externalStorage.mkdirs();
			Log.i(LogTag.FYN.getTag(), "external storage created");
		}
	}

	public File getExternalStorage() {
		return externalStorage;
	}
}
