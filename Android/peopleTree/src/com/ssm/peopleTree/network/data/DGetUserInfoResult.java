package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DGetUserInfoResult extends Data {
	// 사용자 정보 가져오기 프로토콜 결과

	public int status;
	public int userId;
	public int userName;
	public int userPhoneNumber;
	public int edgeType;
	public int edgeStatus;
	public double longtitude;
	public double latitude;
	
	public DGetUserInfoResult(int status, int userId, int userName, int userPhoneNumber, int edgeType, int edgeStatus, double longtitude, double latitude) {
		this.status = status;
		this.userId = userId;
		this.userName = userName;
		this.userPhoneNumber = userPhoneNumber;
		this.edgeType = edgeType;
		this.edgeStatus = edgeStatus;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public DGetUserInfoResult(JSONObject jsonObject) {
		try {			
			status = jsonObject.getInt(STATUS_NAME);
			userId = jsonObject.getInt(USER_ID_NAME);
			userName = jsonObject.getInt(USER_NAME_NAME);
			userPhoneNumber = jsonObject.getInt(USER_PHONE_NAME);
			edgeType = jsonObject.getInt(EDGE_TYPE_NAME);
			edgeStatus = jsonObject.getInt(EDGE_STATUS_NAME);
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
			json.put("status", status);
			json.put("userId", userId);
			json.put("userName", userName);
			json.put("userPhoneNumber", userPhoneNumber);
			json.put("edgeType", edgeType);
			json.put("edegStatus", edgeStatus);
			json.put("longtitude", longtitude);
			json.put("latitude", latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_NAME + "=" + Integer.toString(status);
		result += "&?" + USER_ID_NAME + "=" + Integer.toString(userId);
		result += "&?" + USER_NAME_NAME + "=" + Integer.toString(userName);
		result += "&?" + USER_PHONE_NAME + "=" + Integer.toString(userPhoneNumber);
		result += "&?" + EDGE_TYPE_NAME + "=" + Integer.toString(edgeType);
		result += "&?" + EDGE_STATUS_NAME + "=" + Integer.toString(edgeStatus);
		result += "&?" + LATITUDE_NAME + "=" + Double.toString(latitude);
		result += "&?" + LONGTITUD_NAME + "=" + Double.toString(longtitude);
		
		return result;
	}
}
