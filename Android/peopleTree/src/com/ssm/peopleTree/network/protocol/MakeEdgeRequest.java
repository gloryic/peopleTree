package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class MakeEdgeRequest extends Request {
	// 연결 신청 프로토콜 파라미터
	
	public int ownPhoneNumber;
	public int edgeType;
	public int userNumber;	
	
	public MakeEdgeRequest(int ownPhoneNumber, int edgeType, int userId) {
		this.ownPhoneNumber = ownPhoneNumber;
		this.edgeType = edgeType;
		this.userNumber =userId;
	}
	
	public MakeEdgeRequest(JSONObject jsonObject) {
		try {
			ownPhoneNumber = jsonObject.getInt(OWN_PHONE_KEY);
			edgeType = jsonObject.getInt(EDGE_TYPE_KEY);
			userNumber = jsonObject.getInt(USER_NUMBER_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(OWN_PHONE_KEY, ownPhoneNumber);
			json.put(EDGE_TYPE_KEY, edgeType);
			json.put(USER_NUMBER_KEY, userNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toURI() {
		String result = "";
		result += "?" + OWN_PHONE_KEY + "=" + Integer.toString(ownPhoneNumber);
		result += "&" + EDGE_TYPE_KEY + "=" + Integer.toString(edgeType);
		result += "&" + USER_NUMBER_KEY + "=" + Integer.toString(userNumber);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
