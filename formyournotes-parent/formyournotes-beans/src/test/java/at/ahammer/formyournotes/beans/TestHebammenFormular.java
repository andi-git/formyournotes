package at.ahammer.formyournotes.beans;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;

public class TestHebammenFormular {

	private BeanSerializer serializer;
	private HebammenFormular hebammenFormular;

	@Before
	public void setUp() {
		serializer = new JSONBeanSerializer();
		hebammenFormular = new HebammenFormular();
	}

	@Test
	public void testHebammenFormular() throws SerializationException {
		System.out.println("----------");
		List<FormYourNotesBean<?>> allItemsSortedByRank = hebammenFormular
				.getForm().getAllItemsSortedByRank();
		Assert.assertEquals(7, allItemsSortedByRank.size());
		for (FormYourNotesBean<?> bean : allItemsSortedByRank) {
			System.out.println(bean.getRank());
		}
		System.out.println("----------");
		List<FormYourNotesBean<?>> allTopLevelItemsSortedByRank = hebammenFormular
				.getForm().getAllTopLevelItemsSortedByRank();
		Assert.assertEquals(1, allTopLevelItemsSortedByRank.size());
		for (FormYourNotesBean<?> bean : allTopLevelItemsSortedByRank) {
			System.out.println(bean.getRank());
		}
		System.out.println("----------");
		List<FormYourNotesBean<?>> allChildren = hebammenFormular.getForm()
				.getAllChildren(1);
		Assert.assertEquals(6, allChildren.size());
		for (FormYourNotesBean<?> bean : allChildren) {
			System.out.println(bean.getRank());
		}
		System.out.println("----------");
		Assert.assertEquals("Mutter", hebammenFormular.getForm()
				.getContactBeans().get(0).getDiscription());

		Assert.assertNull(hebammenFormular.getForm().getContactBeans().get(0)
				.getDisplayName());

		hebammenFormular.getForm().setData(hebammenFormular.getData1());
		Assert.assertEquals("Miriam Musterfrau", hebammenFormular.getForm()
				.getContactBeans().get(0).getDisplayName());

		hebammenFormular.getForm().setData(hebammenFormular.getData2());
		Assert.assertEquals("Petra Ahammer", hebammenFormular.getForm()
				.getContactBeans().get(0).getDisplayName());
	}

	@Test
	public void testHebammenFormularJSON() throws SerializationException {
		System.out.println(serializer.serialize(hebammenFormular.getForm()));
		System.out.println(serializer.serialize(hebammenFormular.getData1()));
		System.out.println(serializer.serialize(hebammenFormular.getData2()));
	}

}
