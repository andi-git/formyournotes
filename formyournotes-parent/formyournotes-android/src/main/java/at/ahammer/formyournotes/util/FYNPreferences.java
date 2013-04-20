package at.ahammer.formyournotes.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;

public enum FYNPreferences {

	INSTANCE;

	public static final String PREFS_NAME = "FYNPreferences";
	public static final String PREF_EMAIL = "email";
	public static final String PREF_PASSWORD = "password";

	public void setAccount(Context context, String email, String passwordPlain) {
		String passwordHash = generateHash(passwordPlain);
		Log.i(LogTag.FYN.getTag(), "Save account with email " + email
				+ " and password " + passwordPlain + " -> " + passwordHash);
		getPreferences(context).edit().putString(PREF_EMAIL, email);
		getPreferences(context).edit().putString(PREF_PASSWORD, passwordHash);
	}

	public Account getAccount(Context context) {
		return new Account( //
				getPreferences(context).getString(PREF_EMAIL, ""), //
				getPreferences(context).getString(PREF_PASSWORD, ""));
	}

	private SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREFS_NAME, 0);
	}

	private String generateHash(String passwordPlain) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md5.reset();
		md5.update(passwordPlain.getBytes());
		byte[] result = md5.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			hexString.append(Integer.toHexString(0xFF & result[i]));
		}
		return hexString.toString();
	}

	public static class Account {

		private String email;

		private String passwordHash;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPasswordHash() {
			return passwordHash;
		}

		public void setPasswordHash(String passwordHash) {
			this.passwordHash = passwordHash;
		}

		public Account(String email, String passwordHash) {
			super();
			this.email = email;
			this.passwordHash = passwordHash;
		}

		@Override
		public String toString() {
			return "Account [email=" + email + ", passwordHash=" + passwordHash
					+ "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result
					+ ((passwordHash == null) ? 0 : passwordHash.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Account other = (Account) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (passwordHash == null) {
				if (other.passwordHash != null)
					return false;
			} else if (!passwordHash.equals(other.passwordHash))
				return false;
			return true;
		}

	}
}
