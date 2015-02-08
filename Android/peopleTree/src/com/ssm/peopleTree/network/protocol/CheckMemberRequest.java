package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class CheckMemberRequest extends Request {
	// 디바이스 상태 체크 및 위치 기록,검사 프로토콜 요청
	
	public int groupMemeberId;
	public int parentGroupMemberId;
	public int parentManageMode;
	public int edgyType;
	public int statusCode;
	public int fpId;
	public Double latitude;
	public Double longitude;

	
	public CheckMemberRequest(int groupMemeberId, int parentGroupMemberId, int parentManageMode, int edgyType, int statusCode, int fpId, double latitude, double longtitude) {
		this.groupMemeberId = groupMemeberId;
		this.parentGroupMemberId = parentGroupMemberId;
		this.parentManageMode = parentManageMode;
		this.edgyType = edgyType;
		this.statusCode = statusCode;
		this.fpId = fpId;
		this.latitude = latitude;
		this.longitude = longtitude;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(GROUP_MEMBER_ID_KEY, groupMemeberId);
			json.put(PARENT_GROUP_MEMBER_ID_KEY, parentGroupMemberId);
			json.put(MANAGE_MODE_KEY, parentManageMode);
			json.put(EDGE_TYPE_KEY, edgyType);
			json.put(STATUS_KEY, statusCode);
			json.put(FP_ID_KEY, fpId);
			json.put(LONGITUDE_KEY, latitude);
			json.put(LATITUDE_KEY, longitude);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toURI() {
		String result = "";
		result += "?" + GROUP_MEMBER_ID_KEY + "=" + Integer.toString(groupMemeberId);
		result += "&" + PARENT_GROUP_MEMBER_ID_KEY + "=" + Integer.toString(parentGroupMemberId);
		result += "&" + MANAGE_MODE_KEY + "=" + Integer.toString(parentManageMode);
		result += "&" + EDGE_TYPE_KEY + "=" + Integer.toString(edgyType);
		result += "&" + STATUS_KEY + "=" + Integer.toString(edgyType);
		result += "&" + FP_ID_KEY + "=" + Double.toString(statusCode);
		result += "&" + LATITUDE_KEY + "=";
		if (latitude != null) {
			result += Double.toString(latitude);
		}
		result += "&" + LONGITUDE_KEY + "=";
		if (longitude != null) {
			result += Double.toString(longitude);
		}
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
