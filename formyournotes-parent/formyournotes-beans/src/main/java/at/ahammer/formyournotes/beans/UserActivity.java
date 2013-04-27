package at.ahammer.formyournotes.beans;

import java.util.ArrayList;
import java.util.List;

public class UserActivity {

	private List<FileWriteActivity> fileWriteActivities = new ArrayList<FileWriteActivity>();

	private long lastSync;

	public List<FileWriteActivity> getFileWriteActivities() {
		return fileWriteActivities;
	}

	public List<FileWriteActivity> getFileWriteActivitiesAfterLastSync() {
		return getFileWriteActivitiesAfter(lastSync);
	}

	public List<FileWriteActivity> getFileWriteActivitiesAfter(long timestamp) {
		List<FileWriteActivity> fileWriteActivitiesAfterLastSync = new ArrayList<FileWriteActivity>();
		for (FileWriteActivity fileWriteActivity : fileWriteActivities) {
			if (fileWriteActivity.getTimestamp() >= timestamp) {
				fileWriteActivitiesAfterLastSync.add(fileWriteActivity);
			}
		}
		return fileWriteActivitiesAfterLastSync;
	}

	public void deleteFileWriteActivitesBeforeLastSync() {
		List<Integer> indexesToRemove = new ArrayList<Integer>();
		for (int i = 0; i < fileWriteActivities.size(); i++) {
			if (fileWriteActivities.get(i).getTimestamp() < lastSync) {
				indexesToRemove.add(Integer.valueOf(i));
			}
		}
		for (int i = indexesToRemove.size() - 1; i >= 0; i--) {
			fileWriteActivities.remove(indexesToRemove.get(i).intValue());
		}
	}

	public void setFileWriteActivities(
			List<FileWriteActivity> fileWriteactivities) {
		this.fileWriteActivities = fileWriteactivities;
	}

	public FileWriteActivity addActivity(String fileName, FileWriteType type, String hash) {
		FileWriteActivity fileWriteActivity = new FileWriteActivity(fileName,
				type, hash);
		fileWriteActivities.add(fileWriteActivity);
		return fileWriteActivity;
	}

	public long getLastSync() {
		return lastSync;
	}

	public void setLastSync(long lastSync) {
		this.lastSync = lastSync;
	}
}
