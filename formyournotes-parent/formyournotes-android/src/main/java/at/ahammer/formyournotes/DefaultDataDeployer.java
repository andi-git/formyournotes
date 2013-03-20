package at.ahammer.formyournotes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public class DefaultDataDeployer {

	public static final String FILENAME_DEPLOYED = "deployed.idx";

	private boolean deployed = false;

	private Context context;

	public DefaultDataDeployer(Context context) {
		this.context = context;
		deployed = checkIfDefaultDataIsDeployed();
		if (!deployed) {
			deployFiles();
		}
	}

	private void deployFiles() {
		Log.i(LogTag.FYN.getTag(), "deploying default-files");
		try {
			copyFile("form_1.json");
			copyFile("data_1_1.json");
			copyFile("data_1_2.json");
			copyFile("deployed.idx");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void copyFile(String fileName) throws IOException {
		InputStream inputStream = context.getAssets().open(fileName,
				Context.MODE_PRIVATE);
		OutputStream outputStream = context.openFileOutput(fileName,
				Context.MODE_PRIVATE);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}

	private boolean checkIfDefaultDataIsDeployed() {
		Log.i(LogTag.FYN.getTag(), "check if " + FILENAME_DEPLOYED + " exists");
		boolean exists = new File(context.getFilesDir(), FILENAME_DEPLOYED)
				.exists();
		if (exists) {
			Log.i(LogTag.FYN.getTag(), "default-files exists");
		} else {
			Log.i(LogTag.FYN.getTag(), "default-files don't exist");
		}
		return exists;
	}
}