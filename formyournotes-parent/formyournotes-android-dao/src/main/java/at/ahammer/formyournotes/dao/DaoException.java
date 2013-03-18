package at.ahammer.formyournotes.dao;

public class DaoException extends Exception {

	private static final long serialVersionUID = -5573963166498250875L;

	public DaoException(String text) {
		super(text);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String text, Throwable cause) {
		super(text, cause);
	}
}
