package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class MakeEdgeResponse extends Response {
	// 연결 신청 프로토콜 결과

	public int edgeType;
	public int radius;
	public int groupId;
	public int groupMemberId;
	
	public MakeEdgeResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			edgeType = jsonObj.getInt(EDGE_TYPE_KEY);
			radius = jsonObj.getInt(RADIUS_KEY);
			groupId = jsonObj.getInt(GROUP_ID_KEY);
			groupMemberId = jsonObj.getInt(GROUP_MEMBER_ID_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
