package at.ahammer.formyournotes.security;

import org.junit.Assert;
import org.junit.Test;

public class PHPEncryptDecryptTest {

	@Test
	public void testPhpEncryptDecrypt() throws Exception {
		EncryptDecrypt crypt = new PHPEncryptDecrypt();
		// encrypt
		String text = "Text to Encrypt";
		String encrypted = crypt.bytesToHex(crypt.encrypt(text));
		// decrypt
		String decrypted = new String(crypt.decrypt(encrypted), "UTF-8");
		// test
		Assert.assertEquals("98bb6765b7f5c52f57ccae295eaaef75", encrypted);
		Assert.assertEquals("Text to Encrypt", decrypted);
	}
}
