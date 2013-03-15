package at.ahammer.formyournotes.beans;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.data.CheckBoxData;
import at.ahammer.formyournotes.data.ContactData;
import at.ahammer.formyournotes.data.EditTextData;

public class TestFormSerialization {

	private BeanSerializer beanSerializer;

	@Before
	public void setUp() {
		beanSerializer = new JSONBeanSerializer();
	}

	@Test
	public void testFormSerialization() throws SerializationException,
			IOException {
		FormBean formBean = new FormBean();
		formBean.setName("MyForm");

		CheckBoxGroupBean checkBoxGroupBean = new CheckBoxGroupBean();
		CheckBoxBean checkBoxBean1 = new CheckBoxBean();
		checkBoxBean1.setId(10);
		checkBoxBean1.setRank(10);
		checkBoxBean1.setDiscription("a check 1");
		CheckBoxData checkBoxData1 = new CheckBoxData();
		checkBoxData1.setChecked(true);
		checkBoxBean1.addData(checkBoxData1);
		CheckBoxBean checkBoxBean2 = new CheckBoxBean();
		checkBoxBean2.setId(11);
		checkBoxBean2.setRank(11);
		checkBoxBean2.setDiscription("a check 2");
		CheckBoxData checkBoxData2 = new CheckBoxData();
		checkBoxData2.setChecked(true);
		checkBoxBean2.addData(checkBoxData2);
		checkBoxGroupBean.addCheckBox(checkBoxBean1);
		checkBoxGroupBean.addCheckBox(checkBoxBean2);
		formBean.addCheckBoxGroupBean(checkBoxGroupBean);

		ContactBean contactBean = new ContactBean();
		contactBean.setId(20);
		contactBean.setRank(20);
		contactBean.setDiscription("User");
		ContactData contactData = new ContactData();
		contactData.setAddress("Musterstraße 1a");
		contactData.setDisplayName("Max Mustermann");
		contactData
				.setEmails(Arrays.asList("mail1@test.com", "mail2@test.com"));
		contactData.setFirstName("Max");
		contactData.setLastName("Mustermann");
		contactData.setPhones(Arrays.asList("0123456789", "9876543210"));
		contactBean.addData(contactData);
		formBean.addContactBean(contactBean);

		GroupBean groupBean = new GroupBean();
		groupBean.setId(30);
		groupBean.setRank(30);
		formBean.addGroupBean(groupBean);

		EditTextBean editTextBean1 = new EditTextBean();
		editTextBean1.setId(2);
		editTextBean1.setRank(2);
		editTextBean1.setParent(30);
		EditTextData editTextData1 = new EditTextData();
		editTextData1.setValue("text1");
		editTextBean1.addData(editTextData1);
		EditTextBean editTextBean2 = new EditTextBean();
		editTextBean2.setId(3);
		editTextBean2.setRank(3);
		editTextBean2.setParent(groupBean);
		editTextBean2.setDiscription("text2");
		EditTextData editTextData2 = new EditTextData();
		editTextData2.setValue("text2");
		editTextBean2.addData(editTextData2);
		formBean.addEditTextBean(editTextBean1);
		formBean.addEditTextBean(editTextBean2);

		String jsonStr = beanSerializer.serialize(formBean);
		System.out.println(jsonStr);

		FormBean formBean2 = beanSerializer
				.deserialize(jsonStr, FormBean.class);
		Assert.assertEquals(2, formBean2.getEditTextBeans().size());
		Assert.assertEquals(EditTextBean.class, formBean2.getEditTextBeans()
				.get(0).getClass());
	}
}
