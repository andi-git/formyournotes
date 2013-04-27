package at.ahammer.formyournotes.dao.json;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import at.ahammer.formyournotes.beans.FileWriteActivity;
import at.ahammer.formyournotes.beans.FileWriteType;
import at.ahammer.formyournotes.beans.UserActivity;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.UserActivityDao;

public class UserActivityDaoJSON implements UserActivityDao {

	private final FileHelper<RequiredDataUserActivity> fileHelper;

	private final BeanSerializer serializer;

	// TODO caching?

	public UserActivityDaoJSON(File directory) {
		fileHelper = new FileHelperUserActivity(directory);
		serializer = new JSONBeanSerializer();
	}

	@Override
	public UserActivity getUserActivity() throws DaoException {
		RequiredDataUserActivity requiredData = new RequiredDataUserActivity();
		if (fileHelper.isAvailable(requiredData)) {
			try {
				return serializer.deserialize(fileHelper.getFile(requiredData),
						UserActivity.class);
			} catch (SerializationException e) {
				throw new DaoException(e);
			}
		}
		return null;
	}

	@Override
	public FileWriteActivity addFileWriteActivity(String fileName,
			FileWriteType type, String hash) throws DaoException {
		RequiredDataUserActivity requiredData = new RequiredDataUserActivity();
		if (fileHelper.isAvailable(requiredData)) {
			try {
				UserActivity userActivity = getUserActivity();
				FileWriteActivity fileWriteActivity = userActivity.addActivity(
						fileName, type, hash);
				File file = fileHelper.getFile(requiredData);
				if (file != null) {
					serializer.serialize(userActivity, file);
					return fileWriteActivity;
				} else {
					return null;
				}
			} catch (Exception e) {
				throw new DaoException(e);
			}
		}
		return null;
	}

	@Override
	public UserActivity create() throws DaoException {
		UserActivity userActivity = new UserActivity();
		try {
			serializer.serialize(userActivity,
					fileHelper.createNextFile(new RequiredDataUserActivity()));
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
		return userActivity;
	}

	@Override
	public boolean delete() throws DaoException {
		boolean result = false;
		File file = fileHelper.getFile(new RequiredDataUserActivity());
		if (file != null) {
			result = file.delete();
		}
		return result;
	}

	@Override
	public void setLastSync(Calendar calendar) throws DaoException {
		UserActivity userActivity = getUserActivity();
		userActivity.setLastSync(calendar.getTimeInMillis());
		save(userActivity);
	}
	
	private UserActivity save(UserActivity userActivity) throws DaoException {
		try {
			serializer.serialize(userActivity,
					fileHelper.createNextFile(new RequiredDataUserActivity()));
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
		return userActivity;
	}

	@Override
	public List<FileWriteActivity> getFileWriteActivitiesAfterLastSync()
			throws DaoException {
		return getUserActivity().getFileWriteActivitiesAfterLastSync();
	}

	@Override
	public List<FileWriteActivity> getFileWriteActivitiesAfter(long timestamp)
			throws DaoException {
		return getUserActivity().getFileWriteActivitiesAfter(timestamp);
	}

	@Override
	public void deleteFileWriteActivitesBeforeLastSync() throws DaoException {
		UserActivity userActivity = getUserActivity();
		userActivity.deleteFileWriteActivitesBeforeLastSync();
		save(userActivity);
	}
}
