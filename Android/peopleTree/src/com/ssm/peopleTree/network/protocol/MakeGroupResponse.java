package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class MakeGroupResponse extends Response {
	// 그룹 만들기  프로토콜 파라미터
	
	public MakeGroupResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	void OnSuccess(Object responseData) {
	}
}
