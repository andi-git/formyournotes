package at.ahammer.formyournotes.security;

public interface EncryptDecrypt {

	byte[] hexToBytes(String str);

	String bytesToHex(byte[] data);

	byte[] decrypt(String code) throws Exception;

	byte[] encrypt(String text) throws Exception;

}
