package at.ahammer.formyournotes.dao;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.beans.ContactBean;
import at.ahammer.formyournotes.beans.EditTextBean;
import at.ahammer.formyournotes.beans.FormBean;
import at.ahammer.formyournotes.dao.json.FormDaoJSON;
import at.ahammer.formyournotes.dao.json.UserActivityDaoJSON;

public class FormDaoJSONTest {

	private FormDao formDao;

	private UserActivityDao userActivityDao;

	private TestHelper testHelper;

	@Before
	public void setUp() {
		testHelper = new TestHelper();
		testHelper.createDefaultData();
		formDao = new FormDaoJSON(testHelper.getResourceDir());
		userActivityDao = new UserActivityDaoJSON(testHelper.getResourceDir());
		try {
			userActivityDao.create();
		} catch (DaoException e) {
			Assert.fail(e.getMessage());
		}
	}

	@After
	public void tearDown() {
		testHelper.deleteCreatedFiles();
		try {
			userActivityDao.delete();
		} catch (DaoException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testReadForm() throws DaoException {
		FormBean form = formDao.read(1);
		ContactBean contactBean = (ContactBean) form.getAllChildren(1).get(0);
		Assert.assertEquals("Mutter", contactBean.getDiscription());
		Assert.assertNull(contactBean.getDisplayName());
	}

	@Test
	public void testCRUDForm() throws DaoException {
		FormBean formBean = new FormBean(-1, "MyForm");
		formBean.addEditTextBean(new EditTextBean("MyEditTextBean"));
		formDao.save(formBean);
		Assert.assertTrue(formBean.getId() > 0);
		File savedFile = new File(ClassLoader.getSystemResource("").getFile(),
				"form_" + formBean.getId() + ".json");
		Assert.assertNotNull(savedFile);

		FormBean formBeanRead = formDao.read(formBean.getId());
		Assert.assertEquals(formBean.getId(), formBeanRead.getId());
		Assert.assertEquals("MyForm", formBeanRead.getName());

		formBean.setName("MyUpdatedForm");
		formDao.update(formBean);

		formBeanRead = formDao.read(formBean.getId());
		Assert.assertEquals(formBean.getId(), formBeanRead.getId());
		Assert.assertEquals("MyUpdatedForm", formBeanRead.getName());

		boolean deleted = formDao.delete(formBean);
		Assert.assertTrue(deleted);
		formBeanRead = formDao.read(formBean.getId());
		Assert.assertNull(formBeanRead);
		formBeanRead = formDao.update(formBean);
		Assert.assertNull(formBeanRead);

		Assert.assertEquals(3, userActivityDao.getUserActivity()
				.getFileWriteActivities().size());
	}

	@Test
	public void testReadWithData() throws DaoException {
		FormBean form = formDao.readWithData(1, 1);
		ContactBean contactBean = (ContactBean) form.getAllChildren(1).get(0);
		Assert.assertEquals("Mutter", contactBean.getDiscription());
		Assert.assertEquals("Miriam Musterfrau", contactBean.getDisplayName());
	}
}
