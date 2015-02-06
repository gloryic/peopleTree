package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class GetParentLocResponse extends Response {
	// 관리자에게 유도 프로토콜 파라미터
	
	public int parentId;
	public double latitude;
	public double longtitude;
	
	public GetParentLocResponse(JSONObject jsonObj) {
		super(jsonObj);
	}

	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			parentId = jsonObj.getInt(PARENT_GROUP_MEMBER_ID_KEY);
			latitude = jsonObj.getDouble(LATITUDE_KEY);
			longtitude = jsonObj.getDouble(LONGITUDE_KEY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
