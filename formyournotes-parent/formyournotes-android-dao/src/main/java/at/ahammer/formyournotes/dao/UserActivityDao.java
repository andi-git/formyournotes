package at.ahammer.formyournotes.dao;

import java.util.Calendar;
import java.util.List;

import at.ahammer.formyournotes.beans.FileWriteActivity;
import at.ahammer.formyournotes.beans.FileWriteType;
import at.ahammer.formyournotes.beans.UserActivity;

public interface UserActivityDao {

	UserActivity getUserActivity() throws DaoException;

	FileWriteActivity addFileWriteActivity(String fileName, FileWriteType type)
			throws DaoException;

	UserActivity create() throws DaoException;

	boolean delete() throws DaoException;
	
	void setLastSync(Calendar calendar) throws DaoException;
	
	List<FileWriteActivity> getFileWriteActivitiesAfterLastSync() throws DaoException;
	
	void deleteFileWriteActivitesBeforeLastSync() throws DaoException;
}
