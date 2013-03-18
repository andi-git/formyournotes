package at.ahammer.formyournotes.dao.json;

import java.io.File;

import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.FormDao;

public class FormDaoJSON implements FormDao {

	private final FileHelper<RequiredDataForm> fileHelper;

	private final BeanSerializer serializer;
	
	private final DataDao dataDao;
	
	// TODO caching

	public FormDaoJSON(File directory) {
		fileHelper = new FileHelperForm(directory);
		serializer = new JSONBeanSerializer();
		dataDao = new DataDaoJSON(directory);
	}

	@Override
	public FormBean read(int formId) throws DaoException {
		try {
			RequiredDataForm requiredData = new RequiredDataForm(formId);
			if (fileHelper.isAvailable(requiredData)) {
				return serializer.deserialize(
						fileHelper.getFile(requiredData),
						FormBean.class);
			} else {
				return null;
			}
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public FormBean readWithData(int formId, int dataId) throws DaoException {
		FormBean formBean = read(formId);
		formBean.setData(dataDao.read(formId, dataId));
		return formBean;
	}
	
	@Override
	public FormBean save(FormBean formBean) throws DaoException {
		try {
			int nextId = fileHelper.calculateNextIdOfStandardizedString();
			File nextFile = fileHelper
					.createNextFile(new RequiredDataFormInsert());
			formBean.setId(nextId);
			serializer.serialize(formBean, nextFile);
			return formBean;
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public FormBean update(FormBean formBean) throws DaoException {
		try {
			File file = fileHelper.getFile(new RequiredDataForm(formBean
					.getId()));
			if (file != null) {
				serializer.serialize(formBean, file);
				return formBean;
			} else {
				return null;
			}
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean delete(FormBean formBean) throws DaoException {
		boolean result = false;
		File file = fileHelper.getFile(new RequiredDataForm(formBean.getId()));
		if (file != null) {
			result = file.delete();
		}
		return result;
	}
}
