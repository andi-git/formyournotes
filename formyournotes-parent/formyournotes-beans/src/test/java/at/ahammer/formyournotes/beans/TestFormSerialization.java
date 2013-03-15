package at.ahammer.formyournotes.beans;

import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;

public class TestFormSerialization {

	private BeanSerializer beanSerializer;

	@Before
	public void setUp() {
		beanSerializer = new JSONBeanSerializer();
	}

	@Test
	public void testFormSerialization() {
		Form formBean = new Form(0, 0, "MyForm");
	}
}
