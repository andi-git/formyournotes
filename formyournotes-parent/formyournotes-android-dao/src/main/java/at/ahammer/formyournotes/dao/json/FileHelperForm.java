package at.ahammer.formyournotes.dao.json;

import java.io.File;

public class FileHelperForm extends FileHelper<RequiredDataForm> {

	public FileHelperForm(File directory) {
		super(directory);
	}

	@Override
	protected String getSpecificFileName(RequiredDataForm requiredData) {
		StringBuilder sb = new StringBuilder();
		sb.append(separator);
		sb.append(requiredData.getFormId());
		return sb.toString();
	}

	@Override
	protected RequiredDataForm getRequiredDataWithNextId(RequiredDataForm requiredData) {
		return new RequiredDataForm(calculateNextIdOfStandardizedString());
	}
	
	@Override
	public String getPrefix() {
		return "form";
	}
}
