package at.ahammer.formyournotes.beans;

import org.junit.Assert;
import org.junit.Test;

public class UserActivityTest {

	@Test
	public void testGetFileWriteActivitiesAfterLastSync()
			throws InterruptedException {
		UserActivity userActivity = new UserActivity();
		userActivity.addActivity("name1", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name2", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name3", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name4", FileWriteType.SAVE, "1234");

		Thread.sleep(10);
		userActivity.setLastSync(System.currentTimeMillis());
		Thread.sleep(10);

		userActivity.addActivity("name5", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name6", FileWriteType.SAVE, "1234");

		Assert.assertEquals(6, userActivity.getFileWriteActivities().size());
		Assert.assertEquals(2, userActivity
				.getFileWriteActivitiesAfterLastSync().size());
	}

	@Test
	public void testDeleteFileWriteActivitesBeforeLastSync()
			throws InterruptedException {
		UserActivity userActivity = new UserActivity();
		userActivity.addActivity("name1", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name2", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name3", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name4", FileWriteType.SAVE, "1234");

		Thread.sleep(10);
		userActivity.setLastSync(System.currentTimeMillis());
		Thread.sleep(10);

		userActivity.addActivity("name4", FileWriteType.SAVE, "1234");
		userActivity.addActivity("name5", FileWriteType.SAVE, "1234");

		Assert.assertEquals(6, userActivity.getFileWriteActivities().size());
		userActivity.deleteFileWriteActivitesBeforeLastSync();
		Assert.assertEquals(2, userActivity.getFileWriteActivities().size());
	}
}
