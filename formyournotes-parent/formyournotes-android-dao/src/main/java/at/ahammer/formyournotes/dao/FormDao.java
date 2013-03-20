package at.ahammer.formyournotes.dao;

import java.util.List;

import at.ahammer.formyournotes.beans.FormBean;

public interface FormDao {

	FormBean read(int id) throws DaoException;

	FormBean readWithData(int formId, int dataId) throws DaoException;
	
	FormBean save(FormBean formBean) throws DaoException;

	FormBean update(FormBean formBean) throws DaoException;

	boolean delete(FormBean formBean) throws DaoException;
	
	List<FormBean> all() throws DaoException;
}
