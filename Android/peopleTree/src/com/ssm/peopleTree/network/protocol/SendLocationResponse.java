package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class SendLocationResponse extends Response {
	// 위치 정보 전송 프로토콜 결과

	public int validUserNumber;
	public int edgeStatus;
	public int parentId;
	public int groupCode;
	
	public SendLocationResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			validUserNumber = jsonObj.getInt(VALID_USER_NUM_KEY);
			edgeStatus = jsonObj.getInt(EDGE_STATUS_KEY);
			parentId = jsonObj.getInt(PARENT_GROUP_MEMBER_ID_KEY);
			groupCode = jsonObj.getInt(GROUP_CODE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
