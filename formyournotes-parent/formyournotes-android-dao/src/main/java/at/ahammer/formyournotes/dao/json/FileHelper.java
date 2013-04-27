package at.ahammer.formyournotes.dao.json;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FileHelper<T> {

	public static final String suffix = ".json";
	public static final char separator = '_';
	private final File directory;

	public FileHelper(File directory) {
		if (directory == null) {
			throw new IllegalArgumentException("'directory' must no be null!");
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(
					"'directory' must be a directory but is "
							+ directory.getAbsolutePath());
		}
		this.directory = directory;
	}

	public File getDirectory() {
		return directory;
	}

	protected abstract String getSpecificFileName(T requiredData);

	public String getFileName(T requiredData) {
		StringBuilder sb = new StringBuilder();
		sb.append(getPrefix());
		sb.append(getSpecificFileName(requiredData));
		sb.append(suffix);
		return sb.toString();
	}

	public String createNextFileName(T requiredData) {
		return getFileName(getRequiredDataWithNextId(requiredData));
	}

	public File createNextFile(T requiredData) {
		return new File(directory, getFileName(getRequiredDataWithNextId(requiredData)));
	}

	public boolean isAvailable(T requiredData) {
		boolean result = false;
		if (directory != null && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (getFileName(requiredData).equals(file.getName())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public File getFile(T requiredData) {
		File result = null;
		if (isAvailable(requiredData)) {
			result = new File(directory, getFileName(requiredData));
		}
		return result;
	}

	public List<File> getAllFiles() {
		List<File> files = new ArrayList<File>();
		if (directory != null && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (file.getName().startsWith(getPrefix())) {
					files.add(file);
				}
			}
		}
		Collections.sort(files);
		return files;
	}

	public int calculateNextIdOfStandardizedString() {
		int maxId = 0;
		for (File file : getAllFiles()) {
			String fileName = file.getName();
			int lastIndexOfSeparator = fileName.lastIndexOf(separator);
			int indexOfDot = fileName.indexOf('.');
			String currentIdString = fileName.substring(
					lastIndexOfSeparator + 1, indexOfDot);
			int currentId = Integer.valueOf(currentIdString).intValue();
			if (currentId > maxId) {
				maxId = currentId;
			}
		}
		return maxId + 1;
	}

	public abstract String getPrefix();

	protected abstract T getRequiredDataWithNextId(T requiredData);
}
