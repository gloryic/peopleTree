package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DMakeGroup extends Data {
	// 그룹 만들기  프로토콜 결과

	public int groupId;	
	
	public DMakeGroup(int groupId) {
		this.groupId = groupId;
	}
	
	public DMakeGroup(JSONObject jsonObject) {
		try {
			groupId = jsonObject.getInt(GROUP_ID_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(GROUP_ID_NAME, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + GROUP_ID_NAME + "=" + Integer.toString(groupId);
		return result;
	}
}
