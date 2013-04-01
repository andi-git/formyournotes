package at.ahammer.formyournotes.util;

import java.io.File;

import android.content.Context;
import at.ahammer.formyournotes.file.FileHelper;

public class FYNFileHelper {

	public static final String EXTERNAL_DIRECTORY = "fyn";

	public static File getExternalStorage(Context context) {
		return new FileHelper(context, EXTERNAL_DIRECTORY).getExternalStorage();
	}
}
