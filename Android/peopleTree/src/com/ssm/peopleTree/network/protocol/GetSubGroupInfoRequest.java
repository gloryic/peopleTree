package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetSubGroupInfoRequest extends Protocol {
	// 하위 그룹 인원 정보 프로토콜 파라미터

	public int userNumber;
	public int userType;
	public int groupId;
	public int subRootId;
	
	public GetSubGroupInfoRequest(int userId, int userType, int groupId, int subRootId) {
		this.userNumber = userId;
		this.userType = userType;
		this.groupId = groupId;
		this.subRootId = subRootId;
	}
	
	public GetSubGroupInfoRequest(JSONObject jsonObject) {
		try {
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			userType = jsonObject.getInt(USER_TYPE_KEY);
			groupId = jsonObject.getInt(GROUP_ID_KEY);
			subRootId = jsonObject.getInt(SUB_ROOT_ID_KEY);
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
			json.put(SUB_ROOT_ID_KEY, subRootId);
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
		result += "&" + SUB_ROOT_ID_KEY + "=" + Integer.toString(subRootId);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
