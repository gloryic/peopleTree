package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DMakeEdgeResult extends Data {
	// 연결 신청 프로토콜 결과

	public int status;
	public int edgeType;
	public int radius;
	public int groupId;
	public int groupMemberId;
	
	public DMakeEdgeResult(int status, int edgeType, int radius, int groupId, int groupMemberId) {
		this.status = status;
		this.edgeType = edgeType;
		this.radius = radius;
		this.groupId = groupId;
		this.groupMemberId = groupMemberId;
	}
	
	public DMakeEdgeResult(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_NAME);
			edgeType = jsonObject.getInt(EDGE_TYPE_NAME);
			radius = jsonObject.getInt(RADIUS_NAME);
			groupId = jsonObject.getInt(GROUP_ID_NAME);
			groupMemberId = jsonObject.getInt(GROUP_MEMBER_ID_NAME);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_NAME, status);
			json.put(EDGE_TYPE_NAME, edgeType);
			json.put(RADIUS_NAME, radius);
			json.put(GROUP_ID_NAME, groupId);
			json.put(GROUP_MEMBER_ID_NAME, groupMemberId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_NAME + "=" + Integer.toString(edgeType);
		result += "&?" + EDGE_TYPE_NAME + "=" + Integer.toString(edgeType);
		result += "&?" + RADIUS_NAME + "=" + Integer.toString(radius);
		result += "&?" + GROUP_ID_NAME + "=" + Integer.toString(groupId);
		result += "&?" + GROUP_MEMBER_ID_NAME + "=" + Integer.toString(groupMemberId);
		
		return result;
	}
}
