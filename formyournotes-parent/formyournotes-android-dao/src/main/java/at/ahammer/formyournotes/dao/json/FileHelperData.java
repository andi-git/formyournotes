package at.ahammer.formyournotes.dao.json;

import java.io.File;

public class FileHelperData extends FileHelper<RequiredDataData> {

	public FileHelperData(File directory) {
		super(directory);
	}

	@Override
	protected String getSpecificFileName(RequiredDataData requiredData) {
		StringBuilder sb = new StringBuilder();
		sb.append(separator);
		sb.append(requiredData.getFormId());
		sb.append(separator);
		sb.append(requiredData.getDataId());
		return sb.toString();
	}

	@Override
	protected RequiredDataData getRequiredDataWithNextId(
			RequiredDataData requiredData) {
		return new RequiredDataData(requiredData.getFormId(), calculateNextIdOfStandardizedString());
	}

	@Override
	public String getPrefix() {
		return "data";
	}
}
