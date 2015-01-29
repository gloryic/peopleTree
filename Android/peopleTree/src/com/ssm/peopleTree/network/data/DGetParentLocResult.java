package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DGetParentLocResult extends Data {
	// 관리자에게 유도 프로토콜 파라미터
	
	public int status;
	public int parentId;
	public double latitude;
	public double longtitude;
	
	public DGetParentLocResult(int status, int parentId, double latitude, double longtitude) {
		this.status = status;
		this.parentId = parentId;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public DGetParentLocResult(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_NAME);
			parentId = jsonObject.getInt(PARENT_ID_NAME);
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
			json.put(PARENT_ID_NAME, parentId);
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
		result += "&?" + PARENT_ID_NAME + "=" + Integer.toString(parentId);
		result += "&?" + LATITUDE_NAME + "=" + Double.toString(latitude);
		result += "&?" + LONGTITUD_NAME + "=" + Double.toString(longtitude);
		return result;
	}
}
