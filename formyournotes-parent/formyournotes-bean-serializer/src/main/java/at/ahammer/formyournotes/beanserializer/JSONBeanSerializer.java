package at.ahammer.formyournotes.beanserializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.google.gson.Gson;

public class JSONBeanSerializer implements BeanSerializer {

	@Override
	public String serialize(Object object) throws SerializationException {
		try {
			return new Gson().toJson(object);
		} catch (Exception e) {
			throw new SerializationException("exception during serialization",
					e);
		}
	}

	@Override
	public String serialize(Object object, File location)
			throws SerializationException {
		try {
			if (!location.exists()) {
				location.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(location);
			try {
				return serialize(object, fos);
			} catch (Exception e) {
				throw new SerializationException(
						"exception during serialization", e);
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
						throw new SerializationException(
								"exception during serialization", e);
					}
				}
			}
		} catch (Exception e) {
			throw new SerializationException("exception during serialization",
					e);
		}
	}

	@Override
	public String serialize(Object object, FileOutputStream fos)
			throws SerializationException {
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));
			String result = serialize(object);
			out.append(result);
			out.flush();
			out.close();
			return result;
		} catch (Exception e) {
			throw new SerializationException("exception during serialization",
					e);
		}
	}

	@Override
	public <T> T deserialize(String string, Class<T> clazz)
			throws SerializationException {
		try {
			return new Gson().fromJson(string, clazz);
		} catch (Exception e) {
			throw new SerializationException(
					"exception during deserialization", e);
		}
	}

	@Override
	public <T> T deserialize(File file, Class<T> clazz)
			throws SerializationException {
		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				return deserialize(fis, clazz);
			} catch (Exception e) {
				throw new SerializationException(
						"exception during deserialization", e);
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (Exception e) {
					throw new SerializationException(
							"exception during deserialization", e);
				}
			}
		} catch (Exception e) {
			throw new SerializationException(
					"exception during deserialization", e);
		}
	}

	@Override
	public <T> T deserialize(FileInputStream fis, Class<T> clazz)
			throws SerializationException {
		try {
			StringBuilder contentString = new StringBuilder();
			int content;
			while ((content = fis.read()) != -1) {
				contentString.append((char) content);
			}
			return deserialize(contentString.toString(), clazz);
		} catch (Exception e) {
			throw new SerializationException(
					"exception during deserialization", e);
		}
	}
}