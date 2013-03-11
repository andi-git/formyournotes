package at.ahammer.formyournotes.beanserializer;

public class SerializationException extends Exception {

	private static final long serialVersionUID = 8075883529895511651L;

	public SerializationException(String cause) {
		super(cause);
	}

	public SerializationException(String cause, Throwable t) {
		super(cause, t);
	}
}
