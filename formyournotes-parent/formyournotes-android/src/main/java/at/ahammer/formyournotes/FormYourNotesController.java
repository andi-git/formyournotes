package at.ahammer.formyournotes;

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
import at.ahammer.formyournotes.views.FormView;
import at.ahammer.formyournotes.views.MyR;

public enum FormYourNotesController {

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
			MyR myR) {
		formView = new FormView(formBean, context, myR);
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

	public FormData saveFormData(Context context, FormData formData) {
		DataDao dataDao = getDataDao(context);
		try {
			dataDao.save(formData);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on saving data-bean", e);
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

	public DataDao getDataDao(Context context) {
		return new DataDaoJSON(FYNFileHelper.getExternalStorage(context));
	}

	public FormDao getFormDao(Context context) {
		return new FormDaoJSON(FYNFileHelper.getExternalStorage(context));
	}

}
