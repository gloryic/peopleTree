package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class LoginResponse extends Protocol {

	public int status;
	public String responseData;

	public LoginResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			responseData = jsonObject.getString(RESPONSE_DATA_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_KEY + "=" + status;
		result += "&" + RESPONSE_DATA_KEY + "=" + responseData;
		return result;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_KEY, status);
			json.put(RESPONSE_DATA_KEY, responseData);
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
