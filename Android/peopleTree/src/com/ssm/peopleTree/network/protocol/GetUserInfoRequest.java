package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetUserInfoRequest extends Protocol {
	// 사용자 정보 가져오기 프로토콜 파라미터
	
	public int userNumber;
	public int userType;
	public int groupId;
	
	public GetUserInfoRequest(int userId, int userType, int groupId) {
		this.userNumber = userId;
		this.userType = userType;
		this.groupId = groupId;
	}
	
	public GetUserInfoRequest(JSONObject jsonObject) {
		try {
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			userType = jsonObject.getInt(USER_TYPE_KEY);
			groupId = jsonObject.getInt(GROUP_ID_KEY);
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
			json.put(GROUP_ID_KEY, groupId);
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
		result += "&" + GROUP_ID_KEY + "=" + Integer.toString(groupId);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
