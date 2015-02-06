package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class SetGeoPointResponse extends Response {
	// ���� ����(����) �������� ���

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
