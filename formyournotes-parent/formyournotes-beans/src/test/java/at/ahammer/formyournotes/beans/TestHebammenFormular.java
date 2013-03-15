package at.ahammer.formyournotes.beans;

import org.junit.Test;

public class TestHebammenFormular {

	@Test
	public void testHebammenFormular() {
		HebammenFormular hebammenFormular = new HebammenFormular();
		
		System.out.println("----------");
		for (FormYourNotesBean<?> bean : hebammenFormular.getForm().getAllItemsSortedByRank()) {
			System.out.println(bean.getRank());
		}
		System.out.println("----------");
		for (FormYourNotesBean<?> bean : hebammenFormular.getForm().getAllTopLevelItemsSortedByRank()) {
			System.out.println(bean.getRank());
		}
		System.out.println("----------");
		for (FormYourNotesBean<?> bean : hebammenFormular.getForm().getAllChildren(1)) {
			System.out.println(bean.getRank());
		}
		System.out.println("----------");
		System.out.println(hebammenFormular.getForm().getContactBeans().get(0).getDiscription());
	}
}
