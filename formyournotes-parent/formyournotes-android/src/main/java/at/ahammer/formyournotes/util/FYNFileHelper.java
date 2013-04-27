package at.ahammer.formyournotes.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;
import at.ahammer.formyournotes.file.FileHelper;
import at.ahammer.formyournotes.logging.LogTag;

public enum FYNFileHelper {

	INSTANCE;

	public static final String EXTERNAL_DIRECTORY = "fyn";

	public File getExternalStorage(Context context) {
		return new FileHelper(context, EXTERNAL_DIRECTORY).getExternalStorage();
	}

	public String getFileContent(Context context, String fileName) {
		String result = "";
		try {
			File file = new File(getExternalStorage(context), fileName);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader in = null;
			StringBuilder contentString = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(fis, "UTF8"));
			String line;
			while ((line = in.readLine()) != null) {
				contentString.append(line);
				contentString.append("\n");
			}
			result = contentString.toString();
		} catch (IOException e) {
			Log.i(LogTag.FYN.getTag(), "error on reading file content of "
					+ fileName);
		}
		return result;
	}
}
