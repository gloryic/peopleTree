package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class LoginRequest extends Protocol {

	private static final String PROTOCOL = "/ptree/login";
	private static final String PHONE_PATTERN = "^[0-9]{11}$";
	
	public String id;
	public String password;
	
	private boolean idLogin;
	
	public LoginRequest(String id, String password) {
		this.id = id;
		this.password =password;
		this.idLogin = !id.matches(PHONE_PATTERN);
	}
		
	@Override
	public String toString() {
		String result = PROTOCOL;
		if (idLogin) {
			result += "?" + USER_ID_KEY + "=" + id;
		}
		else {
			result += "?" + USER_PHONE_KEY + "=" + id;
		}
		result += "&" + USER_PASSWORD_KEY + "=" + password;
		return result;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			if (idLogin) {
				json.put(USER_ID_KEY, id);
			}
			else {
				json.put(USER_PHONE_KEY, Integer.parseInt(id));
			}
			json.put(USER_PASSWORD_KEY, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public int getMethod() {
		return Method.POST;
	}

}
