package at.ahammer.formyournotes.security;

import org.junit.Test;

public class MD5HelperTest {

	@Test
	public void generateHash() {
		MD5Helper md5Helper = new MD5Helper();
		System.out.println(md5Helper.generateHash(""));
	}
}
