package at.ahammer.formyournotes.dao.json;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {

	public String generateHash(String string) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md5.reset();
		md5.update(string.getBytes());
		byte[] result = md5.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			hexString.append(Integer.toHexString(0xFF & result[i]));
		}
		return hexString.toString();
	}
}
