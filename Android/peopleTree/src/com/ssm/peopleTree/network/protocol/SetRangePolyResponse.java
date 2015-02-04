package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class SetRangePolyResponse extends Response {
	// 지역 설정(다각형) 프로토콜 결과

	public int validUserNumber;
	public int edgeType;
		
	public SetRangePolyResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			validUserNumber = jsonObj.getInt(VALID_USER_NUM_KEY);
			edgeType = jsonObj.getInt(EDGE_TYPE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
