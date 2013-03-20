package at.ahammer.formyournotes.dao;

import java.util.List;

import at.ahammer.formyournotes.data.FormData;

public interface DataDao {

	FormData read(int formId, int dataId) throws DaoException;

	FormData save(FormData formData) throws DaoException;

	FormData update(FormData formData) throws DaoException;

	boolean delete(FormData formData) throws DaoException;
	
	List<FormData> all() throws DaoException;
	
	List<FormData> allDataForForm(int formId) throws DaoException;
}
