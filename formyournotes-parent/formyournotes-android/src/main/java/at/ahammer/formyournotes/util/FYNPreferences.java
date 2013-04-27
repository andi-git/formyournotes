package at.ahammer.formyournotes.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.preferences.PreferencesHelper;
import at.ahammer.formyournotes.security.MD5Helper;

public enum FYNPreferences {

	INSTANCE;

	public static final String PREFS_NAME = "FYNPreferences";
	public static final String PREF_EMAIL = "email";
	public static final String PREF_PASSWORD = "password";
	private final MD5Helper md5Helper = new MD5Helper();
	private final PreferencesHelper preferencesHelper = new PreferencesHelper();

	public void setAccount(Context context, String email, String passwordPlain) {
		String passwordHash = md5Helper.generateHash(passwordPlain);
		Log.i(LogTag.FYN.getTag(), "Save account with email " + email
				+ " and password " + passwordPlain + " -> " + passwordHash);
		Map<String, String> preferences = new HashMap<String, String>();
		preferences.put(PREF_EMAIL, email);
		preferences.put(PREF_PASSWORD, passwordHash);
		preferencesHelper.setPreferences(context, PREFS_NAME, preferences);
	}

	public Account getAccount(Context context) {
		return new Account( //
				preferencesHelper.getPreferences(context, PREFS_NAME)
						.getString(PREF_EMAIL, ""), //
				preferencesHelper.getPreferences(context, PREFS_NAME)
						.getString(PREF_PASSWORD, ""));
	}

	public static class Account {

		private String email;

		private String passwordHash;

		public Account(String email, String passwordHash) {
			super();
			this.email = email;
			this.passwordHash = passwordHash;
		}

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

		public boolean isValid() {
			return email != null && !"".equals(email) && passwordHash != null
					&& !"".equals(passwordHash);
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
