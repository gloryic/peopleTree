package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class GetSubGroupInfoResponse extends Response {
	// 하위 그룹 인원 정보 프로토콜 파라미터 결과
	public int status;
	public int userNumber;
	public int userName;
	public int userPhoneNumber;
	public int edgeType;
	public int edgeStatus;
	public double longtitude;
	public double latitude; 
	
	public GetSubGroupInfoResponse(JSONObject jsonObj) {
		super(jsonObj);
	}

	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			userNumber = jsonObj.getInt(USER_NUMBER_KEY);
			userName = jsonObj.getInt(USER_NAME_KEY);
			userPhoneNumber = jsonObj.getInt(USER_PHONE_KEY);
			edgeType = jsonObj.getInt(EDGE_TYPE_KEY);
			edgeStatus = jsonObj.getInt(EDGE_STATUS_KEY);
			longtitude = jsonObj.getInt(LONGITUDE_KEY);
			latitude = jsonObj.getInt(LATITUDE_KEY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
