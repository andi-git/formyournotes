package at.ahammer.formyournotes.dao.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.FileStatusDao;
import at.ahammer.formyournotes.dao.FormDao;

public class FormDaoJSON implements FormDao {

	private final FileHelper<RequiredDataForm> fileHelper;

	private final BeanSerializer serializer;

	private final DataDao dataDao;

	private final FileStatusDao fileStatusDao;

	private final MD5Helper md5Helper = new MD5Helper();

	// TODO caching

	public FormDaoJSON(File directory) {
		fileHelper = new FileHelperForm(directory);
		serializer = new JSONBeanSerializer();
		dataDao = new DataDaoJSON(directory);
		fileStatusDao = new FileStatusDaoJSON(directory);
	}

	@Override
	public FormBean read(int formId) throws DaoException {
		FormBean result = null;
		try {
			RequiredDataForm requiredData = new RequiredDataForm(formId);
			if (fileHelper.isAvailable(requiredData)) {
				FormBean formBean = serializer.deserialize(
						fileHelper.getFile(requiredData), FormBean.class);
				if (!formBean.isDeleted()) {
					result = formBean;
				}
			}
			return result;
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
			String fileContent = serializer.serialize(formBean, nextFile);
			fileStatusDao.notify(nextFile.getName(),
					md5Helper.generateHash(fileContent));
			return formBean;
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public FormBean update(FormBean formBean) throws DaoException {
		FormBean result = null;
		try {
			if (!formBean.isDeleted()) {
				File file = fileHelper.getFile(new RequiredDataForm(formBean
						.getId()));
				if (file != null) {
					String fileContent = serializer.serialize(formBean, file);
					fileStatusDao.notify(file.getName(),
							md5Helper.generateHash(fileContent));
					result = formBean;
				}
			}
			return result;
		} catch (SerializationException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean delete(FormBean formBean) throws DaoException {
		formBean.setDeleted(true);
		try {
			File file = fileHelper.getFile(new RequiredDataForm(formBean
					.getId()));
			if (file != null) {
				String fileContent = serializer.serialize(formBean, file);
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

	@Override
	public List<FormBean> all() throws DaoException {
		List<FormBean> allFormBeans = new ArrayList<FormBean>();
		for (FormBean formBean : allWithDeleted()) {
			if (!formBean.isDeleted()) {
				allFormBeans.add(formBean);
			}
		}
		return allFormBeans;
	}

	@Override
	public List<FormBean> allWithDeleted() throws DaoException {
		List<FormBean> allFormBeansWithDeleted = new ArrayList<FormBean>();
		for (File file : fileHelper.getAllFiles()) {
			try {
				allFormBeansWithDeleted.add(serializer.deserialize(file,
						FormBean.class));
			} catch (SerializationException e) {
				throw new DaoException(e);
			}
		}
		return allFormBeansWithDeleted;
	}
}
