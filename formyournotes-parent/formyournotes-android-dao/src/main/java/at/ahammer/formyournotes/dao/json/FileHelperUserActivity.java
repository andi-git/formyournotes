package at.ahammer.formyournotes.dao.json;

import java.io.File;

public class FileHelperUserActivity extends
		FileHelper<RequiredDataUserActivity> {

	public FileHelperUserActivity(File directory) {
		super(directory);
	}

	@Override
	protected String getSpecificFileName(RequiredDataUserActivity requiredData) {
		return "";
	}

	@Override
	protected RequiredDataUserActivity getRequiredDataWithNextId(
			RequiredDataUserActivity requiredData) {
		return null;
	}

	@Override
	public String getPrefix() {
		return "useractivity";
	}
}
