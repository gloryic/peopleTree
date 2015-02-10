package com.ssm.peopleTree.application;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;
import com.ssm.peopleTree.R;
import com.ssm.peopleTree.group.GroupManager;
import com.ssm.peopleTree.map.MapManager;
import com.ssm.peopleTree.network.NetworkManager;
import com.ssm.peopleTree.network.Status;
import com.ssm.peopleTree.network.protocol.GetInfoAllRequest;
import com.ssm.peopleTree.network.protocol.GetInfoAllResponse;
import com.ssm.peopleTree.network.protocol.GetInfoRequest;
import com.ssm.peopleTree.network.protocol.GetInfoResponse;
import com.ssm.peopleTree.network.protocol.LoginRequest;
import com.ssm.peopleTree.network.protocol.LoginResponse;
import com.ssm.peopleTree.network.protocol.LogoutRequest;
import com.ssm.peopleTree.network.protocol.LogoutResponse;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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
	private Listener<JSONObject> onLogoutResponse;
	private Listener<JSONObject> onGetInfoResponse;
	
	private LoginListener loginListener;
	private LogoutListener logoutListener;
	
	public void initialize(Context context) {
		
		loginListener = null;
		logoutListener = null;
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

					NetworkManager.getInstance().request(new GetInfoAllRequest(savedUserNumber), onGetInfoResponse, null);
				}
				else {
					loginListener.onLoginFail(status);
				}
			}
			
		};
		onLogoutResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				if (logoutListener == null)
					return;
				
				LogoutResponse res = new LogoutResponse(arg0);
				Status status = res.getStatus();
				
				if (status == Status.SUCCESS) {
					savedUserNumber = USER_NUMBER_DEFAULT;
					clear();
					MapManager.getInstance().clear();
					
					logoutListener.onLogoutSuccess();
				}
				else {
					logoutListener.onLogoutFail(status);
				}
			}
			
		};
		onGetInfoResponse = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				if (loginListener == null)
					return;
				
				GetInfoAllResponse res = new GetInfoAllResponse(arg0);
				Status status = res.getStatus();
				
				if (res.getStatus() == Status.SUCCESS) {
					MyManager.getInstance().setMyData(res.curData);
					GroupManager.getInstance().setGroup(res.parentData, res.curData, res.children);
					
					//TODO
					
					int groupMemberId = ParseInstallation.getCurrentInstallation().getInt("groupMemberId");		
					
					Log.i("groupMemberId",groupMemberId+"!!!");
					
					if(groupMemberId <= 0)
						ParseInstallation.getCurrentInstallation().put("groupMemberId", res.curData.groupMemberId);
					
					
					ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
						@Override
						public void done(ParseException e) {
							if (e == null) {
								//Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_dialog_success, Toast.LENGTH_SHORT);
								//toast.show();
							} else {
								e.printStackTrace();

								//Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_dialog_failed, Toast.LENGTH_SHORT);
								//toast.show();
							}
						}
					});
					
					
					
					//TODO
					
					
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
			NetworkManager.getInstance().request(new GetInfoAllRequest(savedUserNumber), onGetInfoResponse, null);
		}
		else {
			// TODO
			loginListener.onLoginFail(Status.UNKNOWN_ERROR);
		}
	}
	
	
	public void login(String id, String pw) {
		if (!id.matches("^[ \t\n]*[a-zA-Z]([a-zA-z0-9]*)[ \t\n]*$")) {
			if (loginListener != null) {
				loginListener.onLoginFail(null);
			}
			return;
		}
		id = id.replaceAll("[ \t\n]", "");
		NetworkManager.getInstance().request(new LoginRequest(id, pw), onLoginResponse, null);
	}
	
	public void logout() {
		NetworkManager.getInstance().request(new LogoutRequest(savedUserNumber), onLogoutResponse, null);
	}
	
	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}
	
	public void setLogoutListener(LogoutListener logoutListener) {
		this.logoutListener = logoutListener;
	}
	
	public interface LoginListener {
		
		public void onLoginSuccess();
		public void onLoginFail(Status status);
	}

	public interface LogoutListener {
		
		public void onLogoutSuccess();
		public void onLogoutFail(Status status);
	}
}
