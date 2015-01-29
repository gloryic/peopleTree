package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DGetParentLoc extends Data {
	// 관리자에게 유도 프로토콜 결과
	
	public int status;
	public int userId;
	public double latitude;
	public double longtitude;
	
	public DGetParentLoc(int status, int userId, double latitude, double longtitude) {
		this.status = status;
		this.userId = userId;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public DGetParentLoc(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_NAME);
			userId = jsonObject.getInt(USER_ID_NAME);
			latitude = jsonObject.getDouble(LATITUDE_NAME);
			longtitude = jsonObject.getDouble(LONGTITUD_NAME);
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
			json.put(LATITUDE_NAME, latitude);
			json.put(LONGTITUD_NAME, longtitude);
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
		result += "&?" + LATITUDE_NAME + "=" + Double.toString(latitude);
		result += "&?" + LONGTITUD_NAME + "=" + Double.toString(longtitude);
		return result;
	}
}
