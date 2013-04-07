package at.ahammer.formyournotes.dao.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.ahammer.formyournotes.beans.FileWriteType;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.UserActivityDao;
import at.ahammer.formyournotes.data.FormData;

public class DataDaoJSON implements DataDao {

	private final FileHelper<RequiredDataData> fileHelper;

	private final FileHelper<RequiredDataForm> fileHelperForm;

	private final BeanSerializer serializer;
	
	private final UserActivityDao userActivityDao;

	// TODO caching

	public DataDaoJSON(File directory) {
		fileHelper = new FileHelperData(directory);
		fileHelperForm = new FileHelperForm(directory);
		serializer = new JSONBeanSerializer();
		userActivityDao = new UserActivityDaoJSON(directory);
	}

	@Override
	public FormData read(int formId, int dataId) throws DaoException {
		checkFormExists(formId);
		try {
			RequiredDataData requiredData = new RequiredDataData(formId, dataId);
			if (fileHelper.isAvailable(requiredData)) {
				return serializer.deserialize(fileHelper.getFile(requiredData),
						FormData.class);
			} else {
				return null;
			}
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public FormData save(FormData formData) throws DaoException {
		checkFormExists(formData.getFormId());
		try {
			int nextId = fileHelper.calculateNextIdOfStandardizedString();
			checkIfFormNameAlreadyExists(formData);
			File nextFile = fileHelper
					.createNextFile(new RequiredDataDataInsert(formData
							.getFormId()));
			formData.setDataId(nextId);
			serializer.serialize(formData, nextFile);
			userActivityDao.addFileWriteActivity(nextFile.getName(), FileWriteType.SAVE);
			return formData;
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public FormData update(FormData formData) throws DaoException {
		checkFormExists(formData.getFormId());
		try {
			checkIfFormNameAlreadyExistsInOtherData(formData);
			File file = fileHelper.getFile(new RequiredDataData(formData
					.getFormId(), formData.getDataId()));
			if (file != null) {
				serializer.serialize(formData, file);
				userActivityDao.addFileWriteActivity(file.getName(), FileWriteType.UPDATE);
				return formData;
			} else {
				return null;
			}
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean delete(FormData formData) throws DaoException {
		checkFormExists(formData.getFormId());
		boolean result = false;
		File file = fileHelper.getFile(new RequiredDataData(formData
				.getFormId(), formData.getDataId()));
		if (file != null) {
			result = file.delete();
			userActivityDao.addFileWriteActivity(file.getName(), FileWriteType.DELETE);
		}
		return result;
	}

	private void checkFormExists(int formId) throws DaoException {
		if (!fileHelperForm.isAvailable(new RequiredDataForm(formId))) {
			throw new DaoException("Exception on handling data -> form-id "
					+ formId + " doesn't exist!");
		}
	}

	@Override
	public List<FormData> allDataForForm(int formId) throws DaoException {
		// TODO: caching, no full-select
		List<FormData> allDataForForm = new ArrayList<FormData>();
		for (FormData formData : all()) {
			if (formData.getFormId() == formId) {
				allDataForForm.add(formData);
			}
		}
		return allDataForForm;
	}

	@Override
	public List<FormData> all() throws DaoException {
		List<FormData> allFormData = new ArrayList<FormData>();
		for (File file : fileHelper.getAllFiles()) {
			try {
				allFormData.add(serializer.deserialize(file, FormData.class));
			} catch (SerializationException e) {
				throw new DaoException(e);
			}
		}
		return allFormData;
	}

	@Override
	public FormData readByDisplayName(int formId, String name) throws DaoException {
		FormData result = null;
		for (FormData formData : allDataForForm(formId)) {
			if (formData.getName().equals(name)) {
				result = formData;
				break;
			}
		}
		return result;
	}
	
	private void checkIfFormNameAlreadyExists(FormData formData)
			throws DaoException {
		if (readByDisplayName(formData.getFormId(), formData.getName()) != null) {
			throw new DaoException("formData for name '" + formData.getName() + "' already exists");
		}
	}

	private void checkIfFormNameAlreadyExistsInOtherData(FormData formData)
			throws DaoException {
		FormData formDataReadByDisplayName = readByDisplayName(formData.getFormId(), formData.getName());
		if (formDataReadByDisplayName != null && formDataReadByDisplayName.getDataId() != formData.getDataId()) {
			throw new DaoException("formData for name '" + formData.getName() + "' already exists");
		}
	}
}
