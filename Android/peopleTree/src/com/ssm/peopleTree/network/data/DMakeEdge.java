package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DMakeEdge extends Data {
	// 연결 신청 프로토콜 파라미터
	
	public int ownPhoneNumber;
	public int edgeType;
	public int userId;	
	
	public DMakeEdge(int ownPhoneNumber, int edgeType, int userId) {
		this.ownPhoneNumber = ownPhoneNumber;
		this.edgeType = edgeType;
		this.userId =userId;
	}
	
	public DMakeEdge(JSONObject jsonObject) {
		try {
			ownPhoneNumber = jsonObject.getInt(OWN_PHONE_NAME);
			edgeType = jsonObject.getInt(EDGE_TYPE_NAME);
			userId = jsonObject.getInt(USER_ID_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(OWN_PHONE_NAME, ownPhoneNumber);
			json.put(EDGE_TYPE_NAME, edgeType);
			json.put(USER_ID_NAME, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + OWN_PHONE_NAME + "=" + Integer.toString(ownPhoneNumber);
		result += "&?" + EDGE_TYPE_NAME + "=" + Integer.toString(edgeType);
		result += "&?" + USER_ID_NAME + "=" + Integer.toString(userId);
		return result;
	}
}
