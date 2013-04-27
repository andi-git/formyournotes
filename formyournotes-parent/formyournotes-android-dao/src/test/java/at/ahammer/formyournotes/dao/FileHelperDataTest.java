package at.ahammer.formyournotes.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.dao.json.FileHelperData;
import at.ahammer.formyournotes.dao.json.RequiredDataData;
import at.ahammer.formyournotes.dao.json.RequiredDataDataInsert;

public class FileHelperDataTest {

	private FileHelperData fileHelperData;

	private TestHelper testHelper;

	@Before
	public void setUp() {
		testHelper = new TestHelper();
		testHelper.createDefaultData();
		fileHelperData = new FileHelperData(testHelper.getResourceDir());
	}

	@After
	public void tearDown() {
		testHelper.deleteCreatedFiles();
	}

	@Test
	public void testGetFileName() {
		Assert.assertEquals("data_5_3.json",
				fileHelperData.getFileName(new RequiredDataData(5, 3)));
	}

	@Test
	public void testFileAvailable() {
		Assert.assertFalse(fileHelperData.isAvailable(new RequiredDataData(999,
				999)));
		Assert.assertTrue(fileHelperData
				.isAvailable(new RequiredDataData(1, 1)));
		Assert.assertTrue(fileHelperData
				.isAvailable(new RequiredDataData(1, 2)));
	}

	@Test
	public void testGetFile() {
		Assert.assertNull(fileHelperData
				.getFile(new RequiredDataData(999, 999)));
		Assert.assertEquals("data_1_1.json",
				fileHelperData.getFile(new RequiredDataData(1, 1)).getName());
		Assert.assertEquals("data_1_2.json",
				fileHelperData.getFile(new RequiredDataData(1, 2)).getName());
	}

	@Test
	public void testGetAllFiles() {
		Assert.assertEquals(2, fileHelperData.getAllFiles().size());
	}

	@Test
	public void testGetNextId() {
		Assert.assertEquals(3,
				fileHelperData.calculateNextIdOfStandardizedString());
	}

	@Test
	public void testCreateNextFileName() {
		Assert.assertEquals("data_1_3.json", fileHelperData
				.createNextFileName(new RequiredDataDataInsert(1)));
	}

	@Test
	public void testCreateNextFile() {
		Assert.assertEquals(testHelper.getResourceDir().getAbsolutePath()
				+ "/data_1_3.json",
				fileHelperData.createNextFile(new RequiredDataDataInsert(1))
						.getAbsolutePath());
	}
}
