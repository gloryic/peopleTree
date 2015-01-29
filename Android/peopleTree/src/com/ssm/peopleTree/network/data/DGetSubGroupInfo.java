package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DGetSubGroupInfo extends Data {
	// 하위 그룹 인원 정보 프로토콜 파라미터

	public int userId;
	public int userType;
	public int groupId;
	public int subRootId;
	
	public DGetSubGroupInfo(int userId, int userType, int groupId, int subRootId) {
		this.userId = userId;
		this.userType = userType;
		this.groupId = groupId;
		this.subRootId = subRootId;
	}
	
	public DGetSubGroupInfo(JSONObject jsonObject) {
		try {
			userId = jsonObject.getInt(USER_ID_NAME);
			userType = jsonObject.getInt(USER_TYPE_NAME);
			groupId = jsonObject.getInt(GROUP_ID_NAME);
			subRootId = jsonObject.getInt(SUB_ROOT_ID_NAME);
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
			json.put(SUB_ROOT_ID_NAME, subRootId);
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
		result += "&?" + SUB_ROOT_ID_NAME + "=" + Integer.toString(subRootId);
		return result;
	}
}
