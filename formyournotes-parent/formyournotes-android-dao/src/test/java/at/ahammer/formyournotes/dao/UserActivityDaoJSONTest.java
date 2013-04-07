package at.ahammer.formyournotes.dao;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.beans.FileWriteType;
import at.ahammer.formyournotes.beans.UserActivity;
import at.ahammer.formyournotes.dao.json.UserActivityDaoJSON;

public class UserActivityDaoJSONTest {

	private File resourceDir;

	private UserActivityDao userActivityDao;

	@Before
	public void setUp() {
		resourceDir = new File(ClassLoader.getSystemResource("").getFile());
		userActivityDao = new UserActivityDaoJSON(resourceDir);
	}

	@Test
	public void testCRUD() throws DaoException {
		Assert.assertNotNull(userActivityDao.create());
		userActivityDao.addFileWriteActivity("fileName", FileWriteType.SAVE);
		UserActivity userActivity = userActivityDao.getUserActivity();
		Assert.assertEquals(1, userActivity.getFileWriteActivities().size());
		Assert.assertEquals("fileName", userActivity.getFileWriteActivities().get(0)
				.getFileName());
		Assert.assertTrue(userActivityDao.delete());
	}

	@Test
	public void testLastSync() throws DaoException {
		Assert.assertNotNull(userActivityDao.create());
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Berlin/Europe"));
		calendar.set(Calendar.YEAR, 2013);
		calendar.set(Calendar.MONTH, 3);
		calendar.set(Calendar.DAY_OF_MONTH, 7);
		userActivityDao.setLastSync(calendar);
		UserActivity userActivity = userActivityDao.getUserActivity();
		Assert.assertEquals(calendar.getTimeInMillis(), userActivity.getLastSync());
		Assert.assertTrue(userActivityDao.delete());
	}
}
