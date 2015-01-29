package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DSetRangeAbs extends Data {
	// 지역 설정(절대) 프로토콜 파라미터

	public int userId;
	public int userType;
	public int deviceStatus;
	public int radius;
	
	public DSetRangeAbs(int userId, int userType, int deviceStatus, int radius) {
		this.userId = userId;
		this.userType = userType;
		this.deviceStatus = deviceStatus;
		this.radius = radius;
	}
	
	public DSetRangeAbs(JSONObject jsonObject) {
		try {
			userId = jsonObject.getInt(USER_ID_NAME);
			userType = jsonObject.getInt(USER_TYPE_NAME);
			deviceStatus = jsonObject.getInt(DEVICE_STATUS_NAME);
			radius = jsonObject.getInt(RADIUS_NAME);

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
			json.put(DEVICE_STATUS_NAME, deviceStatus);
			json.put(RADIUS_NAME, radius);
			
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
		result += "&?" + DEVICE_STATUS_NAME + "=" + Integer.toString(deviceStatus);
		result += "&?" + RADIUS_NAME + "=" + Integer.toString(radius);
		return result;
	}
}
