package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class MakeGroupResponse extends Response {
	// �׷� �����  �������� �Ķ����
	
	public MakeGroupResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	void OnSuccess(Object responseData) {
	}
}
