package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DExitGroupResult extends Data {
	// 그룹 나가기 프로토콜 결과
	
	public int status;
	public int groupId;
		
		
	public DExitGroupResult(int status, int groupId) {
		this.status = status;
		this.groupId = groupId;
	}
	
	public DExitGroupResult(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_NAME);
			groupId = jsonObject.getInt(GROUP_ID_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_NAME, status);
			json.put(GROUP_ID_NAME, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_NAME + "=" + Integer.toString(status);
		result += "&?" + GROUP_ID_NAME + "=" + Integer.toString(groupId);
		return result;
	}
}
