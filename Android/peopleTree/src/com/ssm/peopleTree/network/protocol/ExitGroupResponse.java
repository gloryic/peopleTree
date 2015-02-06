package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class ExitGroupResponse extends Response {
	// �׷� ������ �������� ���
	
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
