package at.ahammer.formyournotes.http;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import at.ahammer.formyournotes.beans.LastUpdate;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;

public class HttpConnectorTest {

	@Test
	public void testHttpConnector() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "andreas.ahammer@gmail.com");
		parameters.put("password", "8d98f8681acb33275bb3e983df53fb");
		HttpConnector connector = new HttpConnector(
				"http://www.eppel-boote.at/fyn/lastUpdate.php");
		String result = connector.doPost(parameters);
		System.out.println(result);
		Assert.assertTrue(!"".equals(result));
	}

	@Test
	public void testLastUpdate() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "andreas.ahammer@gmail.com");
		parameters.put("password", "8d98f8681acb33275bb3e983df53fb");
		HttpConnector connector = new HttpConnector(
				"http://www.eppel-boote.at/fyn/lastUpdate.php");
		String result = connector.doPost(parameters);
		BeanSerializer jsonBeanSerializer = new JSONBeanSerializer();
		LastUpdate lastUpdate = jsonBeanSerializer.deserialize(result,
				LastUpdate[].class)[0];
		Assert.assertTrue(lastUpdate.getAsCalendar().get(Calendar.YEAR) >= 2013);
		Assert.assertTrue(lastUpdate.getAsCalendar().get(Calendar.MONTH) >= 3);
		Assert.assertTrue(lastUpdate.getAsCalendar().get(Calendar.DAY_OF_MONTH) >= 7);
	}
}
