package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class ExitGroupResponse extends Protocol {
	// 그룹 나가기 프로토콜 결과
	
	public int status;
	public int groupId;
		
		
	public ExitGroupResponse(int status, int groupId) {
		this.status = status;
		this.groupId = groupId;
	}
	
	public ExitGroupResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			groupId = jsonObject.getInt(GROUP_ID_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_KEY, status);
			json.put(GROUP_ID_KEY, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_KEY + "=" + Integer.toString(status);
		result += "&" + GROUP_ID_KEY + "=" + Integer.toString(groupId);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
