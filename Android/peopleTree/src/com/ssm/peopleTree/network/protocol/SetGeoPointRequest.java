package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.map.GeoPoint;

public class SetGeoPointRequest extends Request {
	// 지역 설정 프로토콜 파라미터
	private static final String REST_PROTOCOL = "/ptree/test/setGeoPoint";

	public int groupMemberId;
	public int radius;
	public int deviceStatus;
	public ArrayList<GeoPoint> points;
	
	public SetGeoPointRequest(int groupMemberId, int radius, int deviceStatus) {
		this(groupMemberId, radius, deviceStatus, null);
	}
	
	public SetGeoPointRequest(int groupMemberId, int deviceStatus, ArrayList<GeoPoint> points) {
		this(groupMemberId, 0, deviceStatus, points);
	}
	
	public SetGeoPointRequest(int groupMemberId, int radius, int deviceStatus, ArrayList<GeoPoint> points) {
		this.groupMemberId = groupMemberId;
		this.radius = radius;
		this.deviceStatus = deviceStatus;
		this.points = new ArrayList<GeoPoint>(points);
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put(GROUP_MEMBER_ID_KEY, groupMemberId);
			jsonObj.put(RADIUS_KEY, radius);
			jsonObj.put(DEVICE_STATUS_KEY, deviceStatus);
			
			JSONArray jsonArr = jsonObj.getJSONArray(POINTS_KEY);
			for (int i = 0; !jsonArr.isNull(i); i++) {
				points.add(new GeoPoint(jsonArr.getJSONObject(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObj;
	}

	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + GROUP_MEMBER_ID_KEY + "=" + Integer.toString(groupMemberId);
		result += "&" + RADIUS_KEY + "=" + Integer.toString(radius);
		result += "&" + DEVICE_STATUS_KEY + "=" + Integer.toString(deviceStatus);
		result += "&" + POINTS_KEY + "=[";
		for (int i = 0; i < points.size(); i++) {
			GeoPoint point = points.get(i);
			result += point.toString();
			if (i < points.size() - 1) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
