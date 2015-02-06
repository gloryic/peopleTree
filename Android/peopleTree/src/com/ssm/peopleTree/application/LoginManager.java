package com.ssm.peopleTree.application;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetUserInfoRequest;
import com.ssm.peopleTree.network.protocol.GetUserInfoResponse;
import com.ssm.peopleTree.network.protocol.LoginRequest;
import com.ssm.peopleTree.network.protocol.LoginResponse;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginManager {
	private volatile static LoginManager instance = null;

	private LoginManager() {
	}
	
	public static LoginManager getInstance() {
		if (null == instance) {
			synchronized (LoginManager.class) {
				instance = new LoginManager();
			}
		}
		
		return instance;
	}
	
	
	private static final String LOGIN_DATA_NAME = "loginData";
	private static final String USER_NUMBER_KEY = "savedUserNumber";
	private static final int USER_NUMBER_DEFAULT = 0;
	
	
	
	private SharedPreferences prefs;
	private int savedUserNumber;
	private boolean loaded;
	
	private Listener<JSONObject> onLoginResponse;
	private Listener<JSONObject> onGetInfoResponse;
	
	private LoginListener loginListener;
	
	public void initialize(Context context) {
		
		loginListener = null;
		onLoginResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				if (loginListener == null)
					return;
				
				LoginResponse res = new LoginResponse(arg0);
				Status status = res.getStatus();
				
				if (status == Status.SUCCESS) {
					savedUserNumber = res.userNumber;
					save();

					NetworkManager.getInstance().request(new GetUserInfoRequest(savedUserNumber), onGetInfoResponse, null);
				}
				else {
					loginListener.onLoginFail(status);
				}
			}
			
		};
		
		onGetInfoResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				if (loginListener == null)
					return;
				
				GetUserInfoResponse res = new GetUserInfoResponse(arg0);
				Status status = res.getStatus();
				
				if (res.getStatus() == Status.SUCCESS) {
					MyManager.getInstance().setMyData(res.mData);
					loginListener.onLoginSuccess();
				}
				else {
					loginListener.onLoginFail(status);
				}
			}
		};
		
		if (!loaded) {
			load(context);
		}
	}

	private void load(Context context) {
		prefs = context.getSharedPreferences(LOGIN_DATA_NAME, Context.MODE_PRIVATE);
		loaded = (prefs != null);
		
		if (loaded) {
			savedUserNumber = prefs.getInt(USER_NUMBER_KEY, USER_NUMBER_DEFAULT);
		}
	}
	
	private void save() {
		try {
			if (!loaded)
				throw new Exception("LoginManager must be used after initializing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();
		ed.putInt(USER_NUMBER_KEY, savedUserNumber);		
		ed.commit();
	}
	
	public void clear() {
		try {
			if (!loaded)
				throw new Exception("LoginManager must be used after initializing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SharedPreferences.Editor ed = prefs.edit();		
		ed.remove(USER_NUMBER_KEY);	
		ed.commit();
	}
	
	public void autoLogin() {
		try {
			if (!loaded)
				throw new Exception("LoginManager must be used after initializing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (savedUserNumber != USER_NUMBER_DEFAULT) {
			NetworkManager.getInstance().request(new GetUserInfoRequest(savedUserNumber), onGetInfoResponse, null);
		}
		else {
			// TODO
			loginListener.onLoginFail(Status.UNKNOWN_ERROR);
		}
	}
	
	
	public void login(String id, String pw) {
		NetworkManager.getInstance().request(new LoginRequest(id, pw), onLoginResponse, null);
	}
	
	public void logout() {
		savedUserNumber = USER_NUMBER_DEFAULT;
		clear();
	}
	
	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}
	
	public interface LoginListener {
		
		public void onLoginSuccess();
		public void onLoginFail(Status status);
	}

}