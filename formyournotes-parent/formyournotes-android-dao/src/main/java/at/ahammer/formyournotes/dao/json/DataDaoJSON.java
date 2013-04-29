package at.ahammer.formyournotes.dao.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.FileStatusDao;
import at.ahammer.formyournotes.data.FormData;

public class DataDaoJSON implements DataDao {

	private final FileHelper<RequiredDataData> fileHelper;

	private final FileHelper<RequiredDataForm> fileHelperForm;

	private final BeanSerializer serializer;

	private final FileStatusDao fileStatusDao;

	private final MD5Helper md5Helper = new MD5Helper();

	// TODO caching

	public DataDaoJSON(File directory) {
		fileHelper = new FileHelperData(directory);
		fileHelperForm = new FileHelperForm(directory);
		serializer = new JSONBeanSerializer();
		fileStatusDao = new FileStatusDaoJSON(directory);
	}

	@Override
	public FormData read(int formId, int dataId) throws DaoException {
		FormData result = null;
		checkFormExists(formId);
		try {
			RequiredDataData requiredData = new RequiredDataData(formId, dataId);
			if (fileHelper.isAvailable(requiredData)) {
				FormData formData = serializer.deserialize(
						fileHelper.getFile(requiredData), FormData.class);
				if (!formData.isDeleted()) {
					result = formData;
				}
			}
			return result;
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
			String fileContent = serializer.serialize(formData, nextFile);
			fileStatusDao.notify(nextFile.getName(),
					md5Helper.generateHash(fileContent));
			return formData;
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public FormData update(FormData formData) throws DaoException {
		FormData result = null;
		checkFormExists(formData.getFormId());
		try {
			if (!formData.isDeleted()) {
				checkIfFormNameAlreadyExistsInOtherData(formData);
				File file = fileHelper.getFile(new RequiredDataData(formData
						.getFormId(), formData.getDataId()));
				if (file != null) {
					String fileContent = serializer.serialize(formData, file);
					fileStatusDao.notify(file.getName(),
							md5Helper.generateHash(fileContent));
					result = formData;
				}
			}
			return result;
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean delete(FormData formData) throws DaoException {
		checkFormExists(formData.getFormId());
		formData.setDeleted(true);
		try {
			File file = fileHelper.getFile(new RequiredDataData(formData
					.getFormId(), formData.getDataId()));
			if (file != null) {
				String fileContent = serializer.serialize(formData, file);
				fileStatusDao.notify(file.getName(),
						md5Helper.generateHash(fileContent));
				return true;
			} else {
				return false;
			}
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	private void checkFormExists(int formId) throws DaoException {
		if (!fileHelperForm.isAvailable(new RequiredDataForm(formId))) {
			throw new DaoException("Exception on handling data -> form-id "
					+ formId + " doesn't exist!");
		}
	}

	@Override
	public List<FormData> allDataForForm(int formId) throws DaoException {
		List<FormData> allDataForForm = new ArrayList<FormData>();
		for (FormData formData : allDataForFormWithDeleted(formId)) {
			if (!formData.isDeleted()) {
				allDataForForm.add(formData);
			}
		}
		return allDataForForm;
	}

	@Override
	public List<FormData> allDataForFormWithDeleted(int formId)
			throws DaoException {
		// TODO: caching, no full-select
		List<FormData> allDataForFormWithDeleted = new ArrayList<FormData>();
		for (FormData formData : allWithDeleted()) {
			if (formData.getFormId() == formId) {
				allDataForFormWithDeleted.add(formData);
			}
		}
		return allDataForFormWithDeleted;
	}

	@Override
	public List<FormData> all() throws DaoException {
		List<FormData> allFormData = new ArrayList<FormData>();
		for (FormData formData : allWithDeleted()) {
			if (!formData.isDeleted()) {
				allFormData.add(formData);
			}
		}
		return allFormData;
	}

	@Override
	public List<FormData> allWithDeleted() throws DaoException {
		List<FormData> allFormDataWithDeleted = new ArrayList<FormData>();
		for (File file : fileHelper.getAllFiles()) {
			try {
				allFormDataWithDeleted.add(serializer.deserialize(file,
						FormData.class));
			} catch (SerializationException e) {
				throw new DaoException(e);
			}
		}
		return allFormDataWithDeleted;
	}

	@Override
	public FormData readByDisplayName(int formId, String name)
			throws DaoException {
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
			throw new DaoException("formData for name '" + formData.getName()
					+ "' already exists");
		}
	}

	private void checkIfFormNameAlreadyExistsInOtherData(FormData formData)
			throws DaoException {
		FormData formDataReadByDisplayName = readByDisplayName(
				formData.getFormId(), formData.getName());
		if (formDataReadByDisplayName != null
				&& formDataReadByDisplayName.getDataId() != formData
						.getDataId()) {
			throw new DaoException("formData for name '" + formData.getName()
					+ "' already exists");
		}
	}
}
