package at.ahammer.formyournotes.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

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

	public String getFileContent(Context context, String fileName)
			throws IOException {
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
			throw e;
		}
		return result;
	}

	public void saveFileContent(Context context, String fileName, String content)
			throws IOException {
		try {
			File file = new File(getExternalStorage(context), fileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "UTF8"));
			out.write(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			Log.i(LogTag.FYN.getTag(), "error on writing file " + fileName
					+ ", content " + content);
			throw e;
		}
	}
}
