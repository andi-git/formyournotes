package at.ahammer.formyournotes.dao;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.beans.FileWriteType;
import at.ahammer.formyournotes.beans.UserActivity;
import at.ahammer.formyournotes.dao.json.UserActivityDaoJSON;

public class UserActivityDaoJSONTest {

	private UserActivityDao userActivityDao;

	private TestHelper testHelper;

	@Before
	public void setUp() {
		testHelper = new TestHelper();
		testHelper.createDefaultData();
		userActivityDao = new UserActivityDaoJSON(testHelper.getResourceDir());
	}

	@After
	public void tearDown() {
		testHelper.deleteCreatedFiles();
	}

	@Test
	public void testCRUD() throws DaoException {
		Assert.assertNotNull(userActivityDao.create());
		userActivityDao.addFileWriteActivity("fileName", FileWriteType.SAVE,
				"qwertz");
		UserActivity userActivity = userActivityDao.getUserActivity();
		Assert.assertEquals(1, userActivity.getFileWriteActivities().size());
		Assert.assertEquals("fileName", userActivity.getFileWriteActivities()
				.get(0).getFileName());
		Assert.assertTrue(userActivityDao.delete());
	}

	@Test
	public void testLastSync() throws DaoException {
		Assert.assertNotNull(userActivityDao.create());
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("Berlin/Europe"));
		calendar.set(Calendar.YEAR, 2013);
		calendar.set(Calendar.MONTH, 3);
		calendar.set(Calendar.DAY_OF_MONTH, 7);
		userActivityDao.setLastSync(calendar);
		UserActivity userActivity = userActivityDao.getUserActivity();
		Assert.assertEquals(calendar.getTimeInMillis(),
				userActivity.getLastSync());
		Assert.assertTrue(userActivityDao.delete());
	}
}
