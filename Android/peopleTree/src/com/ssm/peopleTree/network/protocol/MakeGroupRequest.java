package com.ssm.peopleTree.network.protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.network.NetworkManager;

public class MakeGroupRequest extends Request {
	// ±×·ì ¸¸µé±â  ÇÁ·ÎÅäÄÝ °á°ú
	private static final String REST_PROTOCOL = "/ptree/make/group";
	
	private String userPhoneNumber;
	private String userId;
	private String password;
	private String userName;
	private String groupName;
	
	private Pattern userPhonePattern;
	private Pattern userIdPattern;
	private Pattern passwordPattern;
	private Pattern userNamePattern;
	private Pattern groupNamePattern;
	
	private boolean valid;
	
	public MakeGroupRequest(String userPhoneNumber, String userId, String password, String userName, String groupName) {
		userPhonePattern = Pattern.compile("^[0-9]{10,11}$");
		userIdPattern = Pattern.compile("^[a-zA-Z]\\w{5,19}$");
		passwordPattern = Pattern.compile("^\\S{8,16}$");
		userNamePattern = Pattern.compile("^[°¡-ÆR]{2,}$");
		groupNamePattern = Pattern.compile("^(\\w|[°¡-ÆR]){4,20}$");
		
		valid = true;
		setUserPhoneNumber(userPhoneNumber);
		setUserId(userId);
		setPassword(password);
		setUserName(userName);
		setGroupName(groupName);
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			if (!valid) {
				throw new Exception("Try to convert unvalid request");
			}
			json.put(USER_PHONE_KEY, userPhoneNumber);
			json.put(USER_ID_KEY, userId);
			json.put(USER_PASSWORD_KEY, password);
			json.put(USER_NAME_KEY, userName);
			json.put(GROUP_NAME_KEY, groupName);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		try {
			if (!valid) {
				throw new Exception("Try to convert unvalid request");
			}
			result += "?" + USER_PHONE_KEY + "=" + userPhoneNumber;
			result += "&" + USER_ID_KEY + "=" + userId;
			result += "&" + USER_PASSWORD_KEY + "=" + NetworkManager.getEncodedStr(password);
			result += "&" + USER_NAME_KEY + "=" + NetworkManager.getEncodedStr(userName);
			result += "&" + GROUP_NAME_KEY + "=" + NetworkManager.getEncodedStr(groupName);	
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
	
	public void setGroupName(String groupName) {
		Matcher matcher = groupNamePattern.matcher(groupName);
		valid = valid && matcher.matches();
		
		Log.e("test", groupName + ": " + valid);
		this.groupName = groupName;
	}
	
	public void setPassword(String password) {
		Matcher matcher = passwordPattern.matcher(password);
		valid = valid && matcher.matches();
		
		Log.e("test", password + ": " + valid);
		this.password = password;
	}
	
	public void setUserId(String userId) {
		Matcher matcher = userIdPattern.matcher(userId);
		valid = valid && matcher.matches();

		Log.e("test", userId + ": " + valid);
		this.userId = userId;
	}
	
	public void setUserName(String userName) {
		Matcher matcher = userNamePattern.matcher(userName);
		valid = valid && matcher.matches();
		
		Log.e("test", userName + ": " + valid);
		this.userName = userName;
	}
	
	public void setUserPhoneNumber(String userPhoneNumber) {
		Matcher matcher = userPhonePattern.matcher(userPhoneNumber);
		valid = valid && matcher.matches();
		
		Log.e("test", userPhoneNumber + ": " + valid);
		this.userPhoneNumber = userPhoneNumber;
	}
	
	public boolean isValid() {
		return valid;
	}
}
