package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

public class FileStatus {

	private List<SingleFileStatus> files = new ArrayList<SingleFileStatus>();

	public List<SingleFileStatus> getFiles() {
		return files;
	}

	public void setFiles(List<SingleFileStatus> files) {
		this.files = files;
	}

	public void setSingleFileStatus(SingleFileStatus singleFileStatus) {
		int indexToRemove = -1;
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).getFileName()
					.equals(singleFileStatus.getFileName())) {
				indexToRemove = i;
				break;
			}
		}
		if (indexToRemove > -1) {
			files.remove(indexToRemove);
		}
		files.add(singleFileStatus);
	}

	public SingleFileStatus getSingleFileStatus(String fileName) {
		for (SingleFileStatus singleFileStatus : files) {
			if (singleFileStatus.getFileName().equals(fileName)) {
				return singleFileStatus;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "FileStatus [files=" + files + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileStatus other = (FileStatus) obj;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		return true;
	}
}
