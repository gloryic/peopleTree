package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DExitGroup extends Data {
	// 그룹 나가기 프로토콜 파라미터
	
	public int userId;
	public int groupId;
	public int memberId;
		
	public DExitGroup(int userId, int groupId, int memberId) {
		this.userId = userId;
		this.groupId = groupId;
		this.memberId = memberId;
	}
	
	public DExitGroup(JSONObject jsonObject) {
		try {
			userId = jsonObject.getInt(USER_ID_NAME);
			groupId = jsonObject.getInt(GROUP_ID_NAME);
			memberId = jsonObject.getInt(MEMBER_ID_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_ID_NAME, userId);
			json.put(GROUP_ID_NAME, groupId);
			json.put(MEMBER_ID_NAME, memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_ID_NAME + "=" + Integer.toString(userId);
		result += "&?" + GROUP_ID_NAME + "=" + Integer.toString(groupId);
		result += "&?" + MEMBER_ID_NAME + "=" + Integer.toString(memberId);
		return result;
	}
}
