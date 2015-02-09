package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class LoginRequest extends Request {

	private static final String REST_PROTOCOL = "/ptree/login";
	
	public String userIdOrPhone;
	public String password;
	
	public LoginRequest(String userIdOrPhone, String password) {
		this.userIdOrPhone = userIdOrPhone;
		this.password = password;
	}

	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_ID_OR_PHONE_KEY, userIdOrPhone);
			json.put(USER_PASSWORD_KEY, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
		
	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + USER_ID_OR_PHONE_KEY + "=" + userIdOrPhone;
		result += "&" + USER_PASSWORD_KEY + "=" + password;
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.GET;
	}

}
