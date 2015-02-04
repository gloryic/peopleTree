package com.ssm.peopleTree.data;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginData {
	private static final String LOGIN_DATA_NAME = "loginData";
	
	private static final String LOGIN_KEY = "login";
	private static final String ID_KEY = "savedId";
	private static final String PASSWORD_KEY = "savedPw";
	
	private SharedPreferences prefs;
	
	private boolean logined;
	private String savedId;
	private String savedPw;
	
	private boolean loaded;
	
	public LoginData() {
	}

	public void load(Context context) {
		prefs = context.getSharedPreferences(LOGIN_DATA_NAME, Context.MODE_PRIVATE);
		loaded = (prefs != null);
		
		if (loaded) {
			logined = prefs.getBoolean(LOGIN_KEY, false);
			savedId = prefs.getString(ID_KEY, "");
			savedPw = prefs.getString(PASSWORD_KEY, "");
		}
	}
	
	public void save() {
		try {
			if (!loaded)
				throw new Exception("LoginData:save() must be used after loading");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();
		ed.putBoolean(LOGIN_KEY, logined);
		ed.putString(ID_KEY, savedId);
		ed.putString(PASSWORD_KEY, savedPw);
		
		ed.commit();
	}
	
	public void clear() {
		try {
			if (!loaded)
				throw new Exception("LoginData:clear() must be used after loading");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();
		
		ed.remove(LOGIN_KEY);
		ed.remove(ID_KEY);
		ed.remove(PASSWORD_KEY);
		
		ed.commit();
	}
	
	public String getSavedId() {
		return savedId;
	}
	
	public String getSavedPw() {
		return savedPw;
	}
	
	public boolean isLogined() {
		return logined;
	}
	
	public void setSavedId(String savedId) {
		this.savedId = savedId;
	}
	
	public void setSavedPw(String savedPw) {
		this.savedPw = savedPw;
	}
	
	public void setLogin(boolean login) {
		this.logined = login;
	}
}
