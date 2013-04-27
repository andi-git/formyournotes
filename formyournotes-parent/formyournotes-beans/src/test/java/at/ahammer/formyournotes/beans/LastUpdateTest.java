package at.ahammer.formyournotes.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;

public class LastUpdateTest {

	private BeanSerializer beanSerializer;

	@Before
	public void setUp() {
		this.beanSerializer = new JSONBeanSerializer();
	}

	@Test
	public void testLastUpdate() throws SerializationException {
		String stringToDeserialize = "[{\"lastUpdate\":\"1365343870044\"}]";
		ServerLastUpdate[] lastUpdate = beanSerializer.deserialize(
				stringToDeserialize, ServerLastUpdate[].class);
		Assert.assertTrue(lastUpdate[0].getLastUpdate() >= 1365343870044L);
	}
}
