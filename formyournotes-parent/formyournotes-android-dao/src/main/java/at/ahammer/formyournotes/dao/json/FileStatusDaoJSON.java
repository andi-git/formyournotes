package at.ahammer.formyournotes.dao.json;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

import at.ahammer.formyournotes.beans.FileStatus;
import at.ahammer.formyournotes.beans.SingleFileStatus;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.FileStatusDao;

public class FileStatusDaoJSON implements FileStatusDao {

	private final FileHelper<RequiredDataFileStatus> fileHelper;

	private final BeanSerializer serializer;

	public FileStatusDaoJSON(File directory) {
		fileHelper = new FileHelperFileStatus(directory);
		serializer = new JSONBeanSerializer();
	}

	@Override
	public FileStatus load() throws DaoException {
		FileStatus result = null;
		RequiredDataFileStatus requiredData = new RequiredDataFileStatus();
		if (fileHelper.isAvailable(requiredData)) {
			try {
				result = serializer.deserialize(
						fileHelper.getFile(requiredData), FileStatus.class);
			} catch (SerializationException e) {
				throw new DaoException(e);
			}
		} else {
			result = new FileStatus();
			save(result);
		}
		return result;
	}

	@Override
	public void notify(String fileName, String hash, long timestamp)
			throws DaoException {
		FileStatus fileStatus = load();
		if (fileStatus != null) {
			boolean available = false;
			for (SingleFileStatus singleFileStatus : fileStatus.getFiles()) {
				if (singleFileStatus.getFileName().equals(fileName)) {
					singleFileStatus.setTimestamp(timestamp);
					singleFileStatus.setHash(hash);
					available = true;
					break;
				}
			}
			if (!available) {
				SingleFileStatus singleFileStatus = new SingleFileStatus(
						fileName, hash, timestamp);
				fileStatus.getFiles().add(singleFileStatus);
			}
			save(fileStatus);
		} else {
			throw new DaoException("no FileStatus available");
		}
	}

	@Override
	public void notify(String fileName, String hash) throws DaoException {
		notify(fileName, hash,
				Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"))
						.getTimeInMillis());
	}

	@Override
	public FileStatus save(FileStatus fileStatus) throws DaoException {
		try {
			RequiredDataFileStatus requiredData = new RequiredDataFileStatus();
			File file = null;
			if (!fileHelper.isAvailable(requiredData)) {
				file = fileHelper.createNextFile(requiredData);
			} else {
				file = fileHelper.getFile(requiredData);
			}
			serializer.serialize(fileStatus, file);
		} catch (SerializationException e) {
			throw new DaoException("error on writing FileStatus", e);
		}
		return fileStatus;
	}
}
