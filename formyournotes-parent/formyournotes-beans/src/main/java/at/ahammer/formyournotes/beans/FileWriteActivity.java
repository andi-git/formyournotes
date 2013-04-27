package at.ahammer.formyournotes.beans;

public class FileWriteActivity {

	private long timestamp;

	private FileWriteType type;

	private String fileName;

	private String hash;

	public FileWriteActivity(String fileName, FileWriteType type, String hash) {
		super();
		this.timestamp = System.currentTimeMillis();
		this.type = type;
		this.fileName = fileName;
		this.hash = hash;
	}

	public FileWriteType getType() {
		return type;
	}

	public void setType(FileWriteType type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		FileWriteActivity other = (FileWriteActivity) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileWriteActivity [timestamp=" + timestamp + ", type=" + type
				+ ", fileName=" + fileName + ", hash=" + hash + "]";
	}

}
