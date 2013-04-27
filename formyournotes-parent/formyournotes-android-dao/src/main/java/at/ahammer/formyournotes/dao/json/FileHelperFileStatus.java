package at.ahammer.formyournotes.dao.json;

import java.io.File;

public class FileHelperFileStatus extends FileHelper<RequiredDataFileStatus> {

	public FileHelperFileStatus(File directory) {
		super(directory);
	}

	@Override
	protected String getSpecificFileName(RequiredDataFileStatus requiredData) {
		return "";
	}

	@Override
	protected RequiredDataFileStatus getRequiredDataWithNextId(
			RequiredDataFileStatus requiredData) {
		return null;
	}

	@Override
	public String getPrefix() {
		return "filestatus";
	}
}
