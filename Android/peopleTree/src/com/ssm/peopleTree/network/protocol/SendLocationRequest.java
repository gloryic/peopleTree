package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class SendLocationRequest extends Protocol {
	// ��ġ ���� ���� �������� �Ķ����

	public int userNumber;
	public int userType;
	public int edgeStatus;
	public int deviceStatus;
	public double latitude;
	public double longtitude;
	
	public SendLocationRequest(int userId, int userType, int edgeStatus, int deviceStatus, double latitude, double longtitude) {
		this.userNumber = userId;
		this.userType = userType;
		this.edgeStatus = edgeStatus;
		this.deviceStatus = deviceStatus;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public SendLocationRequest(JSONObject jsonObject) {
		try {
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			userType = jsonObject.getInt(USER_TYPE_KEY);
			edgeStatus = jsonObject.getInt(EDGE_STATUS_KEY);
			deviceStatus = jsonObject.getInt(DEVICE_STATUS_KEY);
			longtitude = jsonObject.getInt(LONGTITUD_KEY);
			latitude = jsonObject.getInt(LATITUDE_KEY);
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
			json.put(EDGE_STATUS_KEY, edgeStatus);
			json.put(DEVICE_STATUS_KEY, deviceStatus);
			json.put(LONGTITUD_KEY, latitude);
			json.put(LATITUDE_KEY, longtitude);
			
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
		result += "&" + EDGE_STATUS_KEY + "=" + Integer.toString(edgeStatus);
		result += "&" + DEVICE_STATUS_KEY + "=" + Integer.toString(deviceStatus);
		result += "&" + LATITUDE_KEY + "=" + Double.toString(latitude);
		result += "&" + LONGTITUD_KEY + "=" + Double.toString(longtitude);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
