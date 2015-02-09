package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class LogoutRequest extends Request {
	
	private static final String REST_PROTOCOL = "/ptree/logout"; 
	
	private int userNumber;
	
	public LogoutRequest(int userNumber) {
		this.userNumber = userNumber;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_NUMBER_KEY, userNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + USER_NUMBER_KEY + "=" + userNumber;
		return result;
	}

	@Override
	public int getMethod() {
		return Method.POST;
	}

}
