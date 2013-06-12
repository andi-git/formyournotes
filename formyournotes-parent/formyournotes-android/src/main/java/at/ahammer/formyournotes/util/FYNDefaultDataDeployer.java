package at.ahammer.formyournotes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
import at.ahammer.formyournotes.beans.HebammenFormular;
import at.ahammer.formyournotes.logging.LogTag;

public class FYNDefaultDataDeployer {

	public static final String FILENAME_DEPLOYED = "deployed.idx";

	private boolean deployed = false;

	private final Context context;

	public FYNDefaultDataDeployer(Context context) {
		this.context = context;
		deployed = checkIfDefaultDataIsDeployed();
		if (!deployed) {
			deployFiles();
		}
	}

	private void deployFiles() {
		Log.i(LogTag.FYN.getTag(), "deploying default-files");
		HebammenFormular hebammenFormular = new HebammenFormular();
		try {
			FYNController.INSTANCE.saveFormBean(context,
					hebammenFormular.getForm());
			// FYNController.INSTANCE.saveFormData(context,
			// hebammenFormular.getData1());
			// FYNController.INSTANCE.saveFormData(context,
			// hebammenFormular.getData2());
			// copyFile("form_1.json");
			// copyFile("data_1_1.json");
			// copyFile("data_1_2.json");
			// copyFile("filestatus.json");
			copyFile("deployed.idx");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void copyFile(String fileName) throws IOException {
		InputStream inputStream = context.getAssets().open(fileName,
				Context.MODE_PRIVATE);
		FileOutputStream outputStream = new FileOutputStream(new File(
				FYNFileHelper.INSTANCE.getExternalStorage(context), fileName));
		try {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	private boolean checkIfDefaultDataIsDeployed() {
		Log.i(LogTag.FYN.getTag(), "check if " + FILENAME_DEPLOYED + " exists");
		boolean exists = new File(
				FYNFileHelper.INSTANCE.getExternalStorage(context),
				FILENAME_DEPLOYED).exists();
		if (exists) {
			Log.i(LogTag.FYN.getTag(), "default-files exists");
		} else {
			Log.i(LogTag.FYN.getTag(), "default-files don't exist");
		}
		return exists;
	}

	public boolean isDeployed() {
		return deployed;
	}
}
