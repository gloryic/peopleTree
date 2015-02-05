package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class SetRangeRelRequest extends Request {
	// ���� ����(���) ��������  �Ķ����
	
	public int userNumber;
	public int userType;
	public int deviceStatus;
	public int radius;
	
	public SetRangeRelRequest(int userId, int userType, int deviceStatus, int radius) {
		this.userNumber = userId;
		this.userType = userType;
		this.deviceStatus = deviceStatus;
		this.radius = radius;
	}
	
	public SetRangeRelRequest(JSONObject jsonObject) {
		try {
			userNumber = jsonObject.getInt(USER_NUMBER_KEY);
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
			json.put(USER_NUMBER_KEY, userNumber);
			json.put(USER_TYPE_KEY, userType);
			json.put(DEVICE_STATUS_KEY, deviceStatus);
			json.put(RADIUS_KEY, radius);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toURI() {
		String result = "";
		result += "?" + USER_NUMBER_KEY + "=" + Integer.toString(userNumber);
		result += "&" + USER_TYPE_KEY + "=" + Integer.toString(userType);
		result += "&" + DEVICE_STATUS_KEY + "=" + Integer.toString(deviceStatus);
		result += "&" + POINTS_KEY + "=" + Integer.toString(radius);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}