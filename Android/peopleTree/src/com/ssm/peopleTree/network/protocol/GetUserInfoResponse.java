package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetUserInfoResponse extends Protocol {
	// 사용자 정보 가져오기 프로토콜 결과

	public int status;
	public int userNumber;
	public int userName;
	public int userPhoneNumber;
	public int edgeType;
	public int edgeStatus;
	public double longtitude;
	public double latitude;
	
	public GetUserInfoResponse(int status, int userId, int userName, int userPhoneNumber, int edgeType, int edgeStatus, double longtitude, double latitude) {
		this.status = status;
		this.userNumber = userId;
		this.userName = userName;
		this.userPhoneNumber = userPhoneNumber;
		this.edgeType = edgeType;
		this.edgeStatus = edgeStatus;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public GetUserInfoResponse(JSONObject jsonObject) {
		try {			
			status = jsonObject.getInt(STATUS_KEY);
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			userName = jsonObject.getInt(USER_NAME_KEY);
			userPhoneNumber = jsonObject.getInt(USER_PHONE_KEY);
			edgeType = jsonObject.getInt(EDGE_TYPE_KEY);
			edgeStatus = jsonObject.getInt(EDGE_STATUS_KEY);
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
			json.put("status", status);
			json.put("userId", userNumber);
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
		result += "?" + STATUS_KEY + "=" + Integer.toString(status);
		result += "&" + USER_NUM_KEY + "=" + Integer.toString(userNumber);
		result += "&" + USER_NAME_KEY + "=" + Integer.toString(userName);
		result += "&" + USER_PHONE_KEY + "=" + Integer.toString(userPhoneNumber);
		result += "&" + EDGE_TYPE_KEY + "=" + Integer.toString(edgeType);
		result += "&" + EDGE_STATUS_KEY + "=" + Integer.toString(edgeStatus);
		result += "&" + LATITUDE_KEY + "=" + Double.toString(latitude);
		result += "&" + LONGTITUD_KEY + "=" + Double.toString(longtitude);
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
