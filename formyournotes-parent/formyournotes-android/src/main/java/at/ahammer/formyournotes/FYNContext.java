package at.ahammer.formyournotes;

import java.io.File;

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

public enum FYNContext {

	INSTANCE;

	// TODO create caching and CRUD-methods for current FormBean and FormData

	private int formId;

	private int dataId;

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		Log.i(LogTag.FYN.getTag(), "set current form-id to " + formId);
		this.formId = formId;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		Log.i(LogTag.FYN.getTag(), "set current data-id to " + dataId);
		this.dataId = dataId;
	}

	public FormBean currentFormBean(Context context) {
		FormDao formDao = getFormDao(context);
		FormBean result = new FormBean();
		try {
			result = formDao.read(formId);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on reading current form-bean", e);
		}
		return result;
	}

	public FormData currentFormData(Context context) {
		DataDao dataDao = getDataDao(context);
		FormData result = new FormData();
		try {
			result = dataDao.read(formId, dataId);
		} catch (DaoException e) {
			Log.e(LogTag.FYN.getTag(), "error on reading current data-bean", e);
		}
		return result;
	}

	public DataDao getDataDao(Context context) {
		return new DataDaoJSON(getExternalStorage(context));
	}

	public FormDao getFormDao(Context context) {
		return new FormDaoJSON(getExternalStorage(context));
	}

	public File getExternalStorage(Context context) {
		return new FileHelper(context).getStorage();
	}
}
