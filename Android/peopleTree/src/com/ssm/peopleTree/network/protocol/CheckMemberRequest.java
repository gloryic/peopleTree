package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class CheckMemberRequest extends Request {
	// 디바이스 상태 체크 및 위치 기록,검사 프로토콜 요청
	
	public int groupMemeberId;
	public int parentGroupMemberId;
	public int parentManageMode;
	public int edgeType;
	public int statusCode;
	public int fpId;
	public Double latitude;
	public Double longitude;

	
	//http://210.118.74.107:3000/ptree/geoutil/checkMember?groupMemberId=20&statusCode=&latitude=7&longitude=2&parentGroupMemberId=41&parentManageMode=220&edgeType=200
	private static final String REST_PROTOCOL = "/ptree/geoutil/checkMember";
	
	public CheckMemberRequest(int groupMemeberId, int parentGroupMemberId, int parentManageMode, int edgyType, int statusCode, int fpId, double latitude, double longtitude) {
		this.groupMemeberId = groupMemeberId;
		this.parentGroupMemberId = parentGroupMemberId;
		this.parentManageMode = parentManageMode;
		this.edgeType = edgyType;
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
			json.put(PARENT_MANAGE_MODE_KEY, parentManageMode);
			json.put(EDGE_TYPE_KEY, edgeType);
			json.put(STATUS_CODE_KEY, statusCode);
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
		String result = REST_PROTOCOL;
		result += "?" + GROUP_MEMBER_ID_KEY + "=" + Integer.toString(groupMemeberId);
		result += "&" + PARENT_GROUP_MEMBER_ID_KEY + "=" + Integer.toString(parentGroupMemberId);
		result += "&" + PARENT_MANAGE_MODE_KEY + "=" + Integer.toString(parentManageMode);
		result += "&" + EDGE_TYPE_KEY + "=" + Integer.toString(edgeType);
		result += "&" + STATUS_CODE_KEY + "=" + Integer.toString(statusCode);
		result += "&" + FP_ID_KEY + "=" + Integer.toString(fpId);
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
