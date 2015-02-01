package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetParentLocResponse extends Protocol {
	// 관리자에게 유도 프로토콜 파라미터
	
	public int status;
	public int parentId;
	public double latitude;
	public double longtitude;
	
	public GetParentLocResponse(int status, int parentId, double latitude, double longtitude) {
		this.status = status;
		this.parentId = parentId;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public GetParentLocResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			parentId = jsonObject.getInt(PARENT_ID_KEY);
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
			json.put(PARENT_ID_KEY, parentId);
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
		result += "&" + PARENT_ID_KEY + "=" + Integer.toString(parentId);
		result += "&" + LATITUDE_KEY + "=" + Double.toString(latitude);
		result += "&" + LONGTITUD_KEY + "=" + Double.toString(longtitude);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
