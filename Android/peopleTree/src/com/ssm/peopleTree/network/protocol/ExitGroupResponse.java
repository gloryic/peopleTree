package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class ExitGroupResponse extends Response {
	// 그룹 나가기 프로토콜 결과
	
	public int groupId;
		
	public ExitGroupResponse(JSONObject jsonObj) {
		super(jsonObj);
	}

	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			groupId = jsonObj.getInt(GROUP_ID_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
