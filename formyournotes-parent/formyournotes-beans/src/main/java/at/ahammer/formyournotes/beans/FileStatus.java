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
