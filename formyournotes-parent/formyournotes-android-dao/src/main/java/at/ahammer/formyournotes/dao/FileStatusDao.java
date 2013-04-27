package at.ahammer.formyournotes.dao;

import at.ahammer.formyournotes.beans.FileStatus;

public interface FileStatusDao {

	FileStatus load() throws DaoException;

	void notify(String fileName, String hash) throws DaoException;

	void notify(String fileName, String hash, long timestamp)
			throws DaoException;
}
