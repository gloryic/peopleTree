package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DGetUserInfo extends Data {
	// 사용자 정보 가져오기 프로토콜 파라미터
	
	public int userId;
	public int userType;
	public int groupId;
	
	public DGetUserInfo(int userId, int userType, int groupId) {
		this.userId = userId;
		this.userType = userType;
		this.groupId = groupId;
	}
	
	public DGetUserInfo(JSONObject jsonObject) {
		try {
			userId = jsonObject.getInt(USER_ID_NAME);
			userType = jsonObject.getInt(USER_TYPE_NAME);
			groupId = jsonObject.getInt(GROUP_ID_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_ID_NAME, userId);
			json.put(USER_TYPE_NAME, userType);
			json.put(GROUP_ID_NAME, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_ID_NAME + "=" + Integer.toString(userId);
		result += "&?" + USER_TYPE_NAME + "=" + Integer.toString(userType);
		result += "&?" + GROUP_ID_NAME + "=" + Integer.toString(groupId);
		return result;
	}
}
