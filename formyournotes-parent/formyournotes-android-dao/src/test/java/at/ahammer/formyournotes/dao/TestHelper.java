package at.ahammer.formyournotes.dao;

import java.io.File;

import org.junit.Assert;

import at.ahammer.formyournotes.beans.HebammenFormular;
import at.ahammer.formyournotes.dao.json.DataDaoJSON;
import at.ahammer.formyournotes.dao.json.FormDaoJSON;

public class TestHelper {

	public void createDefaultData() {
		FormDao formDao = new FormDaoJSON(getResourceDir());
		DataDao dataDao = new DataDaoJSON(getResourceDir());
		HebammenFormular hebammenFormular = new HebammenFormular();
		try {
			formDao.save(hebammenFormular.getForm());
			dataDao.save(hebammenFormular.getData1());
			dataDao.save(hebammenFormular.getData2());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public File getResourceDir() {
		return new File(ClassLoader.getSystemResource("").getFile());
	}

	public void deleteCreatedFiles() {
		for (File file : new File(ClassLoader.getSystemResource("").getFile())
				.listFiles()) {
			if (file.getName().endsWith(".json")) {
				if (!file.delete()) {
					Assert.fail("error on deleting file " + file);
				}
			}
		}
	}
}
