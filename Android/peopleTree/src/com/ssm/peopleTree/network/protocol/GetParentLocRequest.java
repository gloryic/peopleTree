package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetParentLocRequest extends Protocol {
	// 관리자에게 유도 프로토콜 결과
	
	public int status;
	public int userNumber;
	public double latitude;
	public double longtitude;
	
	public GetParentLocRequest(int status, int userId, double latitude, double longtitude) {
		this.status = status;
		this.userNumber = userId;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public GetParentLocRequest(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			latitude = jsonObject.getDouble(LATITUDE_KEY);
			longtitude = jsonObject.getDouble(LONGTITUD_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_KEY, status);
			json.put(USER_NUM_KEY, userNumber);
			json.put(LATITUDE_KEY, latitude);
			json.put(LONGTITUD_KEY, longtitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_KEY + "=" + Integer.toString(status);
		result += "&" + USER_NUM_KEY + "=" + Integer.toString(userNumber);
		result += "&" + LATITUDE_KEY + "=" + Double.toString(latitude);
		result += "&" + LONGTITUD_KEY + "=" + Double.toString(longtitude);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
