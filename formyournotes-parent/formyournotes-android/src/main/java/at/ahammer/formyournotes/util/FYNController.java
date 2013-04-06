package at.ahammer.formyournotes.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.dao.DaoException;
import at.ahammer.formyournotes.dao.DataDao;
import at.ahammer.formyournotes.dao.FormDao;
import at.ahammer.formyournotes.dao.json.DataDaoJSON;
import at.ahammer.formyournotes.dao.json.FormDaoJSON;
import at.ahammer.formyournotes.data.FormData;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.views.FormR;
import at.ahammer.formyournotes.views.FormView;

public enum FYNController {

	INSTANCE;

	// TODO create caching and CRUD-methods for current FormBean and FormData

	private int formId;

	private int dataId;

	private FormView formView;

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		Log.i(LogTag.FYN.getTag(), "set current form-id to " + formId);
		this.formId = formId;
		setDataId(-1);
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		Log.i(LogTag.FYN.getTag(), "set current data-id to " + dataId);
		this.dataId = dataId;
		formView = null;
	}

	public FormView getCurrentFormView(FormBean formBean, Context context,
			FormR formR) {
		formView = new FormView(formBean, context, formR);
		return formView;
	}

	public void updateFormData(Context context) {
		Log.i(LogTag.FYN.getTag(), "update form data");
		if (formView != null) {
			FormData data = formView.getFormBean().getData();
			Log.i(LogTag.FYN.getTag(), "update data " + data.getDataId() + ", "
					+ data.getName());
			updateFormData(context, data);
		}
	}

	public FormBean getCurrentFormBean(Context context) {
		FormDao formDao = getFormDao(context);
		FormBean result = new FormBean();
		try {
			result = formDao.read(formId);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on reading current form-bean", e);
		}
		return result;
	}

	public FormBean getCurrentFormBeanWithCurrentData(Context context) {
		FormDao formDao = getFormDao(context);
		FormBean result = new FormBean();
		try {
			result = formDao.readWithData(formId, dataId);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on reading current form-bean", e);
		}
		return result;
	}

	public FormData getCurrentFormData(Context context) {
		DataDao dataDao = getDataDao(context);
		FormData result = new FormData();
		try {
			result = dataDao.read(formId, dataId);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on reading current data-bean", e);
		}
		return result;
	}

	public FormData updateFormData(Context context, FormData formData) {
		Log.i(LogTag.FYN.getTag(),
				"update form data for " + formData.getDataId() + ", "
						+ formData.getName());
		DataDao dataDao = getDataDao(context);
		try {
			dataDao.update(formData);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on updating data-bean", e);
		}
		return formData;
	}

	public FormData saveFormData(Context context, FormData formData) throws DaoException {
		DataDao dataDao = getDataDao(context);
		try {
			dataDao.save(formData);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on saving data-bean", e);
			throw new DaoException(e);
		}
		return formData;
	}

	public FormBean saveFormBean(Context context, FormBean formBean) {
		FormDao formDao = getFormDao(context);
		try {
			formDao.save(formBean);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on saving form-bean", e);
		}
		return formBean;
	}

	private DataDao getDataDao(Context context) {
		return new DataDaoJSON(FYNFileHelper.getExternalStorage(context));
	}

	private FormDao getFormDao(Context context) {
		return new FormDaoJSON(FYNFileHelper.getExternalStorage(context));
	}

	public List<FormData> allDataForCurrentForm(Context context) {
		return allDataForForm(context, getFormId());
	}

	public List<FormData> allDataForForm(Context context, int formId) {
		List<FormData> formDataList = new ArrayList<FormData>();
		try {
			formDataList.addAll(getDataDao(context).allDataForForm(formId));
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on loading all data from by id "
					+ formId, e);
		}
		return formDataList;
	}

	public List<String> allDataNamesForCurrentForm(Context context) {
		return allDataNamesForForm(context, getFormId());
	}

	public List<String> allDataNamesForForm(Context context, int formId) {
		List<FormData> formDataList = allDataForForm(context, formId);
		List<String> dataNames = new ArrayList<String>();
		for (FormData formData : formDataList) {
			dataNames.add(formData.getName());
		}
		Collections.sort(dataNames);
		return dataNames;
	}

	public FormData getCurrentDataByName(Context context, String name) {
		FormData formData = new FormData();
		try {
			Log.i(LogTag.FYN.getTag(), "search current form " + formId
					+ " by name: " + name);
			formData = getDataDao(context).readByDisplayName(getFormId(), name);
			Log.i(LogTag.FYN.getTag(), "found " + formData.getDataId() + ", "
					+ formData.getName());
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on reading from data by name "
					+ name, e);
		}
		return formData;
	}

	public boolean delete(Context context, FormData formData) {
		boolean deleted = false;
		try {
			getDataDao(context).delete(formData);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on deletin data: " + formData, e);
		}
		return deleted;
	}
}
