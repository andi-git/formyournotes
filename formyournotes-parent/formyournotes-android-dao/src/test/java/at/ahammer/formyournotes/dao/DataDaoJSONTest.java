package at.ahammer.formyournotes.dao;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
		Assert.assertEquals("Miriam Musterfrau", data.getContactData().get(0)
				.getDisplayName());
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
		File savedFile = new File(ClassLoader.getSystemResource("").getFile(),
				"form_" + savedData.getFormId() + "_" + savedData.getDataId()
						+ ".json");
		Assert.assertNotNull(savedFile);

		FormData dataRead = dataDao.read(savedData.getFormId(),
				savedData.getDataId());
		Assert.assertEquals(savedData.getFormId(), dataRead.getFormId());
		Assert.assertEquals(savedData.getDataId(), dataRead.getDataId());
		Assert.assertEquals("MyData", dataRead.getName());
		Assert.assertEquals("MyValue", dataRead.getEditTextData().get(0)
				.getValue());

		data.setName("MyUpdatedData");
		dataDao.update(data);

		dataRead = dataDao.read(data.getFormId(), data.getDataId());
		Assert.assertEquals(savedData.getFormId(), dataRead.getFormId());
		Assert.assertEquals(savedData.getDataId(), dataRead.getDataId());
		Assert.assertEquals("MyUpdatedData", dataRead.getName());
		Assert.assertEquals("MyValue", dataRead.getEditTextData().get(0)
				.getValue());

		boolean deleted = dataDao.delete(data);
		Assert.assertTrue(deleted);
		dataRead = dataDao.read(data.getFormId(), data.getDataId());
		Assert.assertNull(dataRead);
		dataRead = dataDao.update(data);
		Assert.assertNull(dataRead);
	}

	@Test
	public void testDataForForm() throws DaoException {
		List<FormData> formDatas = dataDao.allDataForForm(1);
		Assert.assertEquals(2, formDatas.size());
	}

	@Test
	public void testReadByDisplayName() throws DaoException {
		FormData formData = dataDao.readByDisplayName(1, "Petra Ahammer");
		Assert.assertEquals("Petra Ahammer", formData.getName());
		Assert.assertEquals(2, formData.getDataId());
	}

	@Test
	public void testSaveDuplicateName() throws DaoException {
		FormData savedData = null;
		try {
			FormData data = new FormData(1, -1, "MyData");
			data.add(new EditTextData("MyValue", 1));
			savedData = dataDao.save(data);
			Assert.assertTrue(data.getDataId() > 0);
			File savedFile = new File(ClassLoader.getSystemResource("")
					.getFile(), "form_" + savedData.getFormId() + "_"
					+ savedData.getDataId() + ".json");
			Assert.assertNotNull(savedFile);
			data = new FormData(1, -1, "MyData");
			data.add(new EditTextData("MyNewValue", 1));
			try {
				savedData = dataDao.save(data);
				Assert.fail("here should be an "
						+ DaoException.class.getSimpleName() + " thrown");
			} catch (DaoException e) {
				System.out.println(e.getMessage());
			}
		} finally {
			if (savedData != null) {
				dataDao.delete(savedData);

			}
		}
	}

	@Test
	public void testUpdateDuplicateName() throws DaoException, NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update("bf1942".getBytes());
        byte[] result = md5.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<result.length; i++) {
            hexString.append(Integer.toHexString(0xFF & result[i]));
        }
        System.out.println("MD5: " + hexString.toString());
		FormData savedData1 = null;
		FormData savedData2 = null;
		try {
			FormData myData1 = new FormData(1, -1, "MyData1");
			myData1.add(new EditTextData("MyValue", 1));
			savedData1 = dataDao.save(myData1);
			Assert.assertTrue(myData1.getDataId() > 0);
			File savedFile1 = new File(ClassLoader.getSystemResource("")
					.getFile(), "form_" + savedData1.getFormId() + "_"
					+ savedData1.getDataId() + ".json");
			Assert.assertNotNull(savedFile1);

			FormData myData2 = new FormData(1, -2, "MyData2");
			myData2.add(new EditTextData("MyValue", 1));
			savedData2 = dataDao.save(myData2);
			Assert.assertTrue(myData2.getDataId() > 0);
			File savedFile2 = new File(ClassLoader.getSystemResource("")
					.getFile(), "form_" + savedData2.getFormId() + "_"
					+ savedData2.getDataId() + ".json");
			Assert.assertNotNull(savedFile2);

			savedData2.setName("MyData2");
			savedData2 = dataDao.update(savedData2);
			try {
				savedData2.setName("MyData1");
				savedData2 = dataDao.update(savedData2);
				Assert.fail("here should be an "
						+ DaoException.class.getSimpleName() + " thrown");
			} catch (DaoException e) {
				System.out.println(e.getMessage());
			}
		} finally {
			if (savedData1 != null) {
				dataDao.delete(savedData1);
			}
			if (savedData2 != null) {
				dataDao.delete(savedData2);
			}
		}
	}
}
