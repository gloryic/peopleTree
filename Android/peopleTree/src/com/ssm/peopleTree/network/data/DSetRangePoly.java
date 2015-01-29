package com.ssm.peopleTree.network.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class DSetRangePoly extends Data {
	// 지역 설정(다각형) 프로토콜 파라미터

	public int userId;
	public int userType;
	public int deviceStatus;
	public ArrayList<Double> points;
	
	public DSetRangePoly(int userId, int userType, int deviceStatus, ArrayList<Double> points) {
		this.userId = userId;
		this.userType = userType;
		this.deviceStatus = deviceStatus;
		this.points = new ArrayList<Double>(points);
	}
	
	public DSetRangePoly(JSONObject jsonObject) {
		points = new ArrayList<Double>();
		points.clear();
		
		try {
			userId = jsonObject.getInt(USER_ID_NAME);
			userType = jsonObject.getInt(USER_TYPE_NAME);
			deviceStatus = jsonObject.getInt(DEVICE_STATUS_NAME);
			
			JSONArray jsonArr = jsonObject.getJSONArray(POINTS_NAME);
			int end = jsonArr.length() / 2;
			for (int i = 0; i < end; i++) {
				points.add(jsonArr.getDouble(2 * i));
				points.add(jsonArr.getDouble(2 * i + 1));
			}
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
			
			JSONArray jsonArr = new JSONArray();
			int end = points.size() / 2;
			for (int i = 0; i < end; i++) {
				jsonArr.put(2 * i, points.get(2 * i));
				jsonArr.put(2 * i + 1, points.get(2 * i + 1));
			}
			json.put(POINTS_NAME, jsonArr);
			
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
		result += "&?" + POINTS_NAME + "=" + points.toString();
		return result;
	}
}
