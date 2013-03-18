package at.ahammer.formyournotes.dao.json;

import java.io.File;
import java.util.List;

import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.FormDao;
import at.ahammer.formyournotes.data.FormData;

public class DataDaoJSON implements DataDao {

	private final FileHelper<RequiredDataData> fileHelper;

	private final FileHelper<RequiredDataForm> fileHelperForm;

	private final BeanSerializer serializer;

	// TODO caching

	public DataDaoJSON(File directory) {
		fileHelper = new FileHelperData(directory);
		fileHelperForm = new FileHelperForm(directory);
		serializer = new JSONBeanSerializer();
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
			File nextFile = fileHelper
					.createNextFile(new RequiredDataDataInsert(formData
							.getFormId()));
			formData.setDataId(nextId);
			serializer.serialize(formData, nextFile);
			return formData;
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public FormData update(FormData formData) throws DaoException {
		checkFormExists(formData.getFormId());
		try {
			File file = fileHelper.getFile(new RequiredDataData(formData
					.getFormId(), formData.getDataId()));
			if (file != null) {
				serializer.serialize(formData, file);
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
	public List<FormDao> allDataForForm(int formId) throws DaoException {
		// FIXME implement!
		return null;
	}
}
