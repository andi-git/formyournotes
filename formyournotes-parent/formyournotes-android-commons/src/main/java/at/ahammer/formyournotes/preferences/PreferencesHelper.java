package at.ahammer.formyournotes.preferences;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

	public void setPreferences(Context context, String preferencesName, Map<String, String> preferences) {
		SharedPreferences.Editor editor = getPreferences(context, preferencesName).edit();
		for (String key : preferences.keySet()) {
			editor.putString(key, preferences.get(key));
		}
		editor.commit();
	}
	
	public SharedPreferences getPreferences(Context context, String preferencesName) {
		return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
	}
}
