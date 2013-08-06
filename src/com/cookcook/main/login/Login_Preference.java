package com.cookcook.main.login;





import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Login_Preference {
	private static Login_Preference login_preference;
	private SharedPreferences spLogin;
	
	private Login_Preference(Context context) {
		spLogin = PreferenceManager.getDefaultSharedPreferences(context);
	}
	public static Login_Preference getLogin(Context context) {
		if (login_preference == null) {
			login_preference = new Login_Preference(context);
		}

		return login_preference;
	}
	public Login_Preference putString(String key, String value) {
		spLogin.edit().putString(key, value).commit();
		return login_preference;
	}
	public String getString(String key, String defaultValue) {
		return spLogin.getString(key, defaultValue);
	}
	
	public void deleteAllKey()
	{
		spLogin.edit().clear();
	}
}
