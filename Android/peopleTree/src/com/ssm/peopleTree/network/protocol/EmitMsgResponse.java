package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class EmitMsgResponse extends Response {
	// ��ε� ĳ��Ʈ ���� �޼��� ���� �������� ���
		
	public int sendCount;
	
	public EmitMsgResponse(JSONObject jsonObj) {
		super(jsonObj);
	}

	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			sendCount = jsonObj.getInt(SEND_COUNT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
