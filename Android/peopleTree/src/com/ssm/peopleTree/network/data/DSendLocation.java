package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DSendLocation extends Data {
	// 위치 정보 전송 프로토콜 파라미터

	public int userId;
	public int userType;
	public int edgeStatus;
	public int deviceStatus;
	public double latitude;
	public double longtitude;
	
	public DSendLocation(int userId, int userType, int edgeStatus, int deviceStatus, double latitude, double longtitude) {
		this.userId = userId;
		this.userType = userType;
		this.edgeStatus = edgeStatus;
		this.deviceStatus = deviceStatus;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public DSendLocation(JSONObject jsonObject) {
		try {
			userId = jsonObject.getInt(USER_ID_NAME);
			userType = jsonObject.getInt(USER_TYPE_NAME);
			edgeStatus = jsonObject.getInt(EDGE_STATUS_NAME);
			deviceStatus = jsonObject.getInt(DEVICE_STATUS_NAME);
			longtitude = jsonObject.getInt(LONGTITUD_NAME);
			latitude = jsonObject.getInt(LATITUDE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_ID_NAME, userId);
			json.put(USER_TYPE_NAME, userType);
			json.put(EDGE_STATUS_NAME, edgeStatus);
			json.put(DEVICE_STATUS_NAME, deviceStatus);
			json.put(LONGTITUD_NAME, latitude);
			json.put(LATITUDE_NAME, longtitude);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_ID_NAME + "=" + Integer.toString(userId);
		result += "&?" + USER_TYPE_NAME + "=" + Integer.toString(userType);
		result += "&?" + EDGE_STATUS_NAME + "=" + Integer.toString(edgeStatus);
		result += "&?" + DEVICE_STATUS_NAME + "=" + Integer.toString(deviceStatus);
		result += "&?" + LATITUDE_NAME + "=" + Double.toString(latitude);
		result += "&?" + LONGTITUD_NAME + "=" + Double.toString(longtitude);
		return result;
	}
}
