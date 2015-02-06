package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class SetGeoPointResponse extends Response {
	// 지역 설정(절대) 프로토콜 결과

	public int validUserNumber;
	public int edgeType;
		
	public SetGeoPointResponse(JSONObject jsonObj) {
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
