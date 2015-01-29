package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DGetSubGroupInfoResult extends Data {
	// 하위 그룹 인원 정보 프로토콜 파라미터 결과
	public int status;
	public int userId;
	public int userName;
	public int userPhoneNumber;
	public int edgeType;
	public int edgeStatus;
	public double longtitude;
	public double latitude; 
	
	public DGetSubGroupInfoResult(int status, int userId, int userName, int userPhoneNumber, int edgeType, int edgeStatus, double longtitude, double latitude) {
		this.status = status;
		this.userId = userId;
		this.userName = userName;
		this.userPhoneNumber = userPhoneNumber;
		this.edgeType = edgeType;
		this.edgeStatus = edgeStatus;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public DGetSubGroupInfoResult(JSONObject jsonObject) {
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
			json.put(STATUS_NAME, status);
			json.put(USER_ID_NAME, userId);
			json.put(USER_NAME_NAME, userName);
			json.put(USER_PHONE_NAME, userPhoneNumber);
			json.put(EDGE_TYPE_NAME, edgeType);
			json.put(EDGE_STATUS_NAME, edgeStatus);
			json.put(LONGTITUD_NAME, longtitude);
			json.put(LATITUDE_NAME, latitude);
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
