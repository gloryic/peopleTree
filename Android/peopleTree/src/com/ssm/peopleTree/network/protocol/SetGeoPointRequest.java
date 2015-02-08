package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.map.GeoPoint;

public class SetGeoPointRequest extends Request {
	// ���� ���� �������� �Ķ����
	private static final String REST_PROTOCOL = "/ptree/test/setGeoPoint";

	public int groupMemberId;
	public int radius;
	public ArrayList<GeoPoint> points;
	
	public SetGeoPointRequest(int groupMemberId, int radius) {
		this(groupMemberId, radius, null);
	}
	
	public SetGeoPointRequest(int groupMemberId, ArrayList<GeoPoint> points) {
		this(groupMemberId, 0, points);
	}
	
	public SetGeoPointRequest(int groupMemberId, int radius, ArrayList<GeoPoint> points) {
		this.groupMemberId = groupMemberId;
		this.radius = radius;
		this.points = new ArrayList<GeoPoint>(points);
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put(GROUP_MEMBER_ID_KEY, groupMemberId);
			jsonObj.put(RADIUS_KEY, radius);
			
			JSONArray jsonArr = new JSONArray();
			for (GeoPoint point : points) {
				jsonArr.put(point);
			}
			jsonObj.put(POINTS_KEY, jsonArr);

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
