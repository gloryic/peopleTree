package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class ExitGroupRequest extends Protocol {
	// �׷� ������ �������� �Ķ����
	
	public int userNumber;
	public int groupId;
	public int memberId;
		
	public ExitGroupRequest(int userId, int groupId, int memberId) {
		this.userNumber = userId;
		this.groupId = groupId;
		this.memberId = memberId;
	}
	
	public ExitGroupRequest(JSONObject jsonObject) {
		try {
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			groupId = jsonObject.getInt(GROUP_ID_KEY);
			memberId = jsonObject.getInt(MEMBER_ID_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_NUM_KEY, userNumber);
			json.put(GROUP_ID_KEY, groupId);
			json.put(MEMBER_ID_KEY, memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_NUM_KEY + "=" + Integer.toString(userNumber);
		result += "&" + GROUP_ID_KEY + "=" + Integer.toString(groupId);
		result += "&" + MEMBER_ID_KEY + "=" + Integer.toString(memberId);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
