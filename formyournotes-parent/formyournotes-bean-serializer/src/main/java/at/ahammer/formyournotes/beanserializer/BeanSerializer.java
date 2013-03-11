package at.ahammer.formyournotes.beanserializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public interface BeanSerializer {

	String serialize(Object object) throws SerializationException;

	String serialize(Object object, File location)
			throws SerializationException;

	String serialize(Object object, FileOutputStream fos)
			throws SerializationException;

	<T> T deserialize(String string, Class<T> clazz) throws SerializationException;

	<T> T deserialize(File file, Class<T> clazz) throws SerializationException;
	
	<T> T deserialize(FileInputStream fis, Class<T> clazz) throws SerializationException;
}
