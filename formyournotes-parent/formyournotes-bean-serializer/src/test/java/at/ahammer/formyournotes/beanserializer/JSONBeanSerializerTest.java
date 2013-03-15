package at.ahammer.formyournotes.beanserializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JSONBeanSerializerTest {

	private static final String SIMPLE_BEAN_A_JSON = "" + 
		"{\n" + //
		"  \"string\": \"a string\",\n" + 
		"  \"i\": 42,\n" + 
		"  \"list\": [\n" + 
		"    {\n" + 
		"      \"bstring\": \"string1\",\n" + 
		"      \"b\": true\n" + 
		"    },\n" + 
		"    {\n" + 
		"      \"bstring\": \"string2\",\n" + 
		"      \"b\": false\n" + 
		"    }\n" + 
		"  ]\n" + 
		"}";
	private BeanSerializer beanSerializer;

	@Before
	public void setUp() {
		beanSerializer = new JSONBeanSerializer();
	}

	@Test
	public void testSerialize() throws SerializationException {
		System.out.println(beanSerializer.serialize(createSimpleBeanA()));
		Assert.assertEquals(SIMPLE_BEAN_A_JSON,
				beanSerializer.serialize(createSimpleBeanA()));
	}

	@Test
	public void testSerializeToFile() throws SerializationException,
			IOException {
		File file = getFileInTestClasses("testSerializeToFile.json");
		String result = beanSerializer.serialize(createSimpleBeanA(), file);
		Assert.assertEquals(SIMPLE_BEAN_A_JSON, result);
		assertSimpleBeanAFile(file);
	}

	@Test
	public void testSerializeToFileOutputStream()
			throws SerializationException, IOException {
		File file = getFileInTestClasses("testSerializeToFileOutputStream.json");
		FileOutputStream fos = new FileOutputStream(file);
		String result = beanSerializer.serialize(createSimpleBeanA(), fos);
		Assert.assertEquals(SIMPLE_BEAN_A_JSON, result);
		fos.flush();
		fos.close();
		assertSimpleBeanAFile(file);
	}

	@Test
	public void testDeserialize() throws SerializationException {
		SimpleBeanA simpleBeanA = beanSerializer.deserialize(
				SIMPLE_BEAN_A_JSON, SimpleBeanA.class);
		assertSimpleBeanAObject(simpleBeanA);
	}

	@Test
	public void testDeserializeFile() throws SerializationException,
			IOException {
		// create file
		File file = getFileInTestClasses("testDeserializeFromFile.json");
		if (!file.exists()) {
			file.createNewFile();
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF8"));
		out.append(SIMPLE_BEAN_A_JSON);
		out.flush();
		out.close();
		// deserialize file
		SimpleBeanA simpleBeanA = beanSerializer.deserialize(file,
				SimpleBeanA.class);
		assertSimpleBeanAObject(simpleBeanA);
	}

	@Test
	public void testDeserializeFileInputStream() throws SerializationException,
			IOException {
		// create file
		File file = getFileInTestClasses("testDeserializeFromFileInputStream.json");
		if (!file.exists()) {
			file.createNewFile();
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF8"));
		out.append(SIMPLE_BEAN_A_JSON);
		out.flush();
		out.close();
		// deserialize file
		FileInputStream fis = new FileInputStream(file);
		SimpleBeanA simpleBeanA = beanSerializer.deserialize(fis,
				SimpleBeanA.class);
		fis.close();
		assertSimpleBeanAObject(simpleBeanA);
	}

	private SimpleBeanA createSimpleBeanA() {
		SimpleBeanA simpleBeanA = new SimpleBeanA("a string", 42);
		simpleBeanA.getList().add(new SimpleBeanB("string1", true));
		simpleBeanA.getList().add(new SimpleBeanB("string2", false));
		return simpleBeanA;
	}

	private void assertSimpleBeanAObject(SimpleBeanA simpleBeanA) {
		Assert.assertNotNull(simpleBeanA);
		Assert.assertEquals("a string", simpleBeanA.getString());
		Assert.assertEquals(42, simpleBeanA.getI());
		Assert.assertEquals(2, simpleBeanA.getList().size());
		Assert.assertEquals("string1", simpleBeanA.getList().get(0)
				.getBstring());
		Assert.assertTrue(simpleBeanA.getList().get(0).isB());
		Assert.assertEquals("string2", simpleBeanA.getList().get(1)
				.getBstring());
		Assert.assertFalse(simpleBeanA.getList().get(1).isB());
	}

	private void assertSimpleBeanAFile(File file) throws FileNotFoundException,
			IOException {
		FileInputStream fis = new FileInputStream(file);
		StringBuilder contentString = new StringBuilder();
		int content;
		while ((content = fis.read()) != -1) {
			contentString.append((char) content);
		}
		Assert.assertEquals(SIMPLE_BEAN_A_JSON, contentString.toString());
	}

	private File getFileInTestClasses(String name) {
		return new File(ClassLoader.getSystemResource("").getFile(), name);
	}
}
