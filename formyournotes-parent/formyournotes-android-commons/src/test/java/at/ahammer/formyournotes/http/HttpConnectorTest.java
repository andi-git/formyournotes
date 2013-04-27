package at.ahammer.formyournotes.http;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import at.ahammer.formyournotes.beans.ServerData;
import at.ahammer.formyournotes.beans.ServerLastUpdate;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;

public class HttpConnectorTest {

	@Test
	public void testHttpConnector() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "test@user.at");
		parameters.put("password", "5f4dcc3b5aa765d61d8327deb882cf99");
		HttpConnector connector = new HttpConnector(
				"http://www.eppel-boote.at/fyn/lastUpdate.php");
		String result = connector.doPost(parameters);
		System.out.println(result);
		Assert.assertTrue(!"".equals(result));
	}

	@Test
	@Ignore
	public void testLastUpdate() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "test@user.at");
		parameters.put("password", "5f4dcc3b5aa765d61d8327deb882cf99");
		HttpConnector connector = new HttpConnector(
				"http://www.eppel-boote.at/fyn/lastUpdate.php");
		String result = connector.doPost(parameters);
		BeanSerializer jsonBeanSerializer = new JSONBeanSerializer();
		ServerLastUpdate lastUpdate = jsonBeanSerializer.deserialize(result,
				ServerLastUpdate[].class)[0];
		Assert.assertTrue(lastUpdate.getAsCalendar().get(Calendar.YEAR) >= 2013);
		Assert.assertTrue(lastUpdate.getAsCalendar().get(Calendar.MONTH) >= 3);
		Assert.assertTrue(lastUpdate.getAsCalendar().get(Calendar.DAY_OF_MONTH) >= 7);
	}

	@Test
	public void testItemsForUserWithoutContent() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "test@user.at");
		parameters.put("password", "5f4dcc3b5aa765d61d8327deb882cf99");
		HttpConnector connector = new HttpConnector(
				"http://www.eppel-boote.at/fyn/itemsForUserWithoutContent.php");
		String result = connector.doPost(parameters);
		BeanSerializer jsonBeanSerializer = new JSONBeanSerializer();
		ServerData[] serverData = jsonBeanSerializer.deserialize(result,
				ServerData[].class);
		Assert.assertEquals(4, serverData.length);
		Assert.assertEquals(
				"ServerData [user=2, filename=filename, content=null, timestamp=1365345585091, deleted=0, hash=1234]",
				serverData[0].toString());
		Assert.assertEquals(
				"ServerData [user=2, filename=filename2, content=null, timestamp=1365345585095, deleted=1, hash=1235]",
				serverData[1].toString());
	}

	@Test
	public void testAddItems() throws Exception {
		ServerData serverData1 = new ServerData();
		serverData1.setContent("myContent");
		serverData1.setDeleted(false);
		serverData1.setFilename("myFilename");
		serverData1.setHash("ahash");
		serverData1.setTimestamp(1234567890L);
		
		ServerData serverData2 = new ServerData();
		serverData2.setContent("myContent2");
		serverData2.setDeleted(true);
		serverData2.setFilename("myFilename2");
		serverData2.setHash("ahash2");
		serverData2.setTimestamp(1234567891L);
		
		ServerData[] serverDataArray = new ServerData[] { serverData1, serverData2 };
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "test@user.at");
		parameters.put("password", "5f4dcc3b5aa765d61d8327deb882cf99");
		String items = new JSONBeanSerializer().serialize(serverDataArray);
		items = items.replaceAll("\n", "");
		parameters.put("items", items);
		parameters.put("persist", "true");
		HttpConnector connector = new HttpConnector(
				"http://www.eppel-boote.at/fyn/addItems.php");
		String result = connector.doPost(parameters);
		Assert.assertEquals("success", result);
	}
}
