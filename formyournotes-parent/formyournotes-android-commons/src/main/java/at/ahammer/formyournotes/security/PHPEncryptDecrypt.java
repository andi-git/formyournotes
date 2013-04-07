package at.ahammer.formyournotes.security;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @see <a
 *      href="https://github.com/SeRPRo/Android-PHP-Encrypt-Decrypt">Android-PHP-Encrypt-Decrypt</a>
 * @see <a
 *      href="http://www.androidsnippets.com/encrypt-decrypt-between-android-and-php">some
 *      changes in comment below</a>
 */
public class PHPEncryptDecrypt {

	private static final String iv = "defabc3216549870";
	private IvParameterSpec ivspec;
	private SecretKeySpec keyspec;
	private Cipher cipher;
	private static final String secretKey = "0789456123cbadef";

	public PHPEncryptDecrypt() {
		ivspec = new IvParameterSpec(iv.getBytes());
		keyspec = new SecretKeySpec(secretKey.getBytes(), "AES");
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] encrypt(String text) throws Exception {
		if (text == null || text.length() == 0)
			throw new IllegalArgumentException("Empty string");
		byte[] encrypted = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException("[encrypt] " + e.getMessage());
		}
		return encrypted;
	}

	public byte[] decrypt(String code) throws Exception {
		if (code == null || code.length() == 0)
			throw new IllegalArgumentException("Empty string");
		byte[] decrypted = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			decrypted = cipher.doFinal(hexToBytes(code));
		} catch (Exception e) {
			throw new RuntimeException("[decrypt] " + e.getMessage());
		}
		return decrypted;
	}

	public static String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		}
		int len = data.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			if ((data[i] & 0xFF) < 16) {
				str = str + " " + java.lang.Integer.toHexString(data[i] & 0xFF);
			} else {
				str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
			}
		}
		return str;
	}

	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}
}
