package at.ahammer.formyournotes.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.dao.json.FileHelperForm;
import at.ahammer.formyournotes.dao.json.RequiredDataForm;
import at.ahammer.formyournotes.dao.json.RequiredDataFormInsert;

public class FileHelperFormTest {

	private FileHelperForm fileHelperForm;

	private TestHelper testHelper;

	@Before
	public void setUp() {
		testHelper = new TestHelper();
		testHelper.createDefaultData();
		fileHelperForm = new FileHelperForm(testHelper.getResourceDir());
	}

	@After
	public void tearDown() {
		testHelper.deleteCreatedFiles();
	}

	@Test
	public void testGetFileName() {
		Assert.assertEquals("form_5.json",
				fileHelperForm.getFileName(new RequiredDataForm(5)));
	}

	@Test
	public void testFileAvailable() {
		Assert.assertFalse(fileHelperForm
				.isAvailable(new RequiredDataForm(999)));
		Assert.assertTrue(fileHelperForm.isAvailable(new RequiredDataForm(1)));
	}

	@Test
	public void testGetFile() {
		Assert.assertNull(fileHelperForm.getFile(new RequiredDataForm(999)));
		Assert.assertEquals("form_1.json",
				fileHelperForm.getFile(new RequiredDataForm(1)).getName());
	}

	@Test
	public void testGetAllFiles() {
		Assert.assertEquals(1, fileHelperForm.getAllFiles().size());
	}

	@Test
	public void testGetNextId() {
		Assert.assertEquals(2,
				fileHelperForm.calculateNextIdOfStandardizedString());
	}

	@Test
	public void testCreateNextFileName() {
		Assert.assertEquals("form_2.json",
				fileHelperForm.createNextFileName(new RequiredDataFormInsert()));
	}

	@Test
	public void testCreateNextFile() {
		Assert.assertEquals(testHelper.getResourceDir().getAbsolutePath()
				+ "/form_2.json",
				fileHelperForm.createNextFile(new RequiredDataFormInsert())
						.getAbsolutePath());
	}
}
