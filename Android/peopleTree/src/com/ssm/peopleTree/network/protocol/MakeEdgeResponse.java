package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class MakeEdgeResponse extends Protocol {
	// 연결 신청 프로토콜 결과

	public int status;
	public int edgeType;
	public int radius;
	public int groupId;
	public int groupMemberId;
	
	public MakeEdgeResponse(int status, int edgeType, int radius, int groupId, int groupMemberId) {
		this.status = status;
		this.edgeType = edgeType;
		this.radius = radius;
		this.groupId = groupId;
		this.groupMemberId = groupMemberId;
	}
	
	public MakeEdgeResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			edgeType = jsonObject.getInt(EDGE_TYPE_KEY);
			radius = jsonObject.getInt(RADIUS_KEY);
			groupId = jsonObject.getInt(GROUP_ID_KEY);
			groupMemberId = jsonObject.getInt(ROUP_MEMBER_ID_KEY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_KEY, status);
			json.put(EDGE_TYPE_KEY, edgeType);
			json.put(RADIUS_KEY, radius);
			json.put(GROUP_ID_KEY, groupId);
			json.put(ROUP_MEMBER_ID_KEY, groupMemberId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_KEY + "=" + Integer.toString(edgeType);
		result += "&" + EDGE_TYPE_KEY + "=" + Integer.toString(edgeType);
		result += "&" + RADIUS_KEY + "=" + Integer.toString(radius);
		result += "&" + GROUP_ID_KEY + "=" + Integer.toString(groupId);
		result += "&" + ROUP_MEMBER_ID_KEY + "=" + Integer.toString(groupMemberId);
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
