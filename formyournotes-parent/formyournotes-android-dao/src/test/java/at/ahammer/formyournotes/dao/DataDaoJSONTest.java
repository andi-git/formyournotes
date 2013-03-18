package at.ahammer.formyournotes.dao;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.dao.json.DataDaoJSON;
import at.ahammer.formyournotes.data.EditTextData;
import at.ahammer.formyournotes.data.FormData;

public class DataDaoJSONTest {

	private File resourceDir;

	private DataDao dataDao;

	@Before
	public void setUp() {
		resourceDir = new File(ClassLoader.getSystemResource("").getFile());
		dataDao = new DataDaoJSON(resourceDir);
	}

	@Test
	public void testReadForm() throws DaoException {
		FormData data = dataDao.read(1, 1);
		Assert.assertEquals("Miriam Musterfrau", data.getContactData().get(0).getDisplayName());
	}

	@Test(expected = DaoException.class)
	public void testFormNotAvailble() throws DaoException {
		dataDao.read(Integer.MAX_VALUE, 1);
	}

	@Test
	public void testCRUDForm() throws DaoException {
		FormData data = new FormData(1, -1, "MyData");
		data.add(new EditTextData("MyValue", 1));
		FormData savedData = dataDao.save(data);
		Assert.assertTrue(data.getDataId() > 0);
		File savedFile = new File(ClassLoader.getSystemResource("").getFile(), "form_" + savedData.getFormId() + "_" + savedData.getDataId() + ".json");
		Assert.assertNotNull(savedFile);
		
		FormData dataRead = dataDao.read(savedData.getFormId(), savedData.getDataId());
		Assert.assertEquals(savedData.getFormId(), dataRead.getFormId());
		Assert.assertEquals(savedData.getDataId(), dataRead.getDataId());
		Assert.assertEquals("MyData", dataRead.getName());
		Assert.assertEquals("MyValue", dataRead.getEditTextData().get(0).getValue());
		
		data.setName("MyUpdatedData");
		dataDao.update(data);
		
		dataRead = dataDao.read(data.getFormId(), data.getDataId());
		Assert.assertEquals(savedData.getFormId(), dataRead.getFormId());
		Assert.assertEquals(savedData.getDataId(), dataRead.getDataId());
		Assert.assertEquals("MyUpdatedData", dataRead.getName());
		Assert.assertEquals("MyValue", dataRead.getEditTextData().get(0).getValue());
		
		boolean deleted = dataDao.delete(data);
		Assert.assertTrue(deleted);
		dataRead = dataDao.read(data.getFormId(), data.getDataId());
		Assert.assertNull(dataRead);
		dataRead = dataDao.update(data);
		Assert.assertNull(dataRead);
	}
}
