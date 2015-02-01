package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;

public class SetRangePolyRequest extends Protocol {
	// 지역 설정(다각형) 프로토콜 파라미터

	public int userNumber;
	public int userType;
	public int deviceStatus;
	public ArrayList<Double> points;
	
	public SetRangePolyRequest(int userId, int userType, int deviceStatus, ArrayList<Double> points) {
		this.userNumber = userId;
		this.userType = userType;
		this.deviceStatus = deviceStatus;
		this.points = new ArrayList<Double>(points);
	}
	
	public SetRangePolyRequest(JSONObject jsonObject) {
		points = new ArrayList<Double>();
		points.clear();
		
		try {
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			userType = jsonObject.getInt(USER_TYPE_KEY);
			deviceStatus = jsonObject.getInt(DEVICE_STATUS_KEY);
			
			JSONArray jsonArr = jsonObject.getJSONArray(POINTS_KEY);
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
			json.put(USER_NUM_KEY, userNumber);
			json.put(USER_TYPE_KEY, userType);
			json.put(DEVICE_STATUS_KEY, deviceStatus);
			
			JSONArray jsonArr = new JSONArray();
			int end = points.size() / 2;
			for (int i = 0; i < end; i++) {
				jsonArr.put(2 * i, points.get(2 * i));
				jsonArr.put(2 * i + 1, points.get(2 * i + 1));
			}
			json.put(POINTS_KEY, jsonArr);
			
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
		result += "&" + DEVICE_STATUS_KEY + "=" + Integer.toString(deviceStatus);
		result += "&" + POINTS_KEY + "=" + points.toString();
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
