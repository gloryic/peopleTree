package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class SetRangeAbsRequest extends Protocol {
	// 지역 설정(절대) 프로토콜 파라미터

	public int userNumber;
	public int userType;
	public int deviceStatus;
	public int radius;
	
	public SetRangeAbsRequest(int userId, int userType, int deviceStatus, int radius) {
		this.userNumber = userId;
		this.userType = userType;
		this.deviceStatus = deviceStatus;
		this.radius = radius;
	}
	
	public SetRangeAbsRequest(JSONObject jsonObject) {
		try {
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			userType = jsonObject.getInt(USER_TYPE_KEY);
			deviceStatus = jsonObject.getInt(DEVICE_STATUS_KEY);
			radius = jsonObject.getInt(RADIUS_KEY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_NUM_KEY, userNumber);
			json.put(USER_TYPE_KEY, userType);
			json.put(DEVICE_STATUS_KEY, deviceStatus);
			json.put(RADIUS_KEY, radius);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_NUM_KEY + "=" + Integer.toString(userNumber);
		result += "&" + USER_TYPE_KEY + "=" + Integer.toString(userType);
		result += "&" + DEVICE_STATUS_KEY + "=" + Integer.toString(deviceStatus);
		result += "&" + RADIUS_KEY + "=" + Integer.toString(radius);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
