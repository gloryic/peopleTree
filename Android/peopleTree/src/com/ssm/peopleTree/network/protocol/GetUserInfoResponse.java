package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class GetUserInfoResponse extends Response {
	// 사용자 정보 가져오기 프로토콜 결과
	
	public GetUserInfoResponse(JSONObject jsonObj) {
		super(jsonObj);
	}

	public String userId;
	public String userName;
	public int userNumber;
	public int userPhoneNumber;
	
	public int groupId;
	public int groupMemberId;
	public int parentGroupMemberId;
	
	public int edgeStatus;
	
	public int manageMode;
	public int managedLocationRadius;
	public int managingTotalNumber;
	public int managingNumber;
	
	public Double longitude;
	public Double latitude;
	
	@Override
	void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			userId = jsonObj.getString(USER_ID_KEY);
			userName = jsonObj.getString(USER_NAME_KEY);
			userNumber = jsonObj.getInt(USER_NUMBER_KEY);
			userPhoneNumber = jsonObj.getInt(USER_PHONE_KEY);
			
			groupMemberId = jsonObj.getInt(GROUP_MEMBER_ID_KEY);
			parentGroupMemberId = jsonObj.getInt(PARENT_GROUP_MEMBER_ID_KEY);
			groupId = jsonObj.getInt(GROUP_ID_KEY);
			
			edgeStatus = jsonObj.getInt(EDGE_STATUS_KEY);
			
			manageMode = jsonObj.getInt(MANAGE_MODE_KEY);
			managedLocationRadius = jsonObj.getInt(MANAGE_LOCATION_RADIUS_KEY);
			managingTotalNumber = jsonObj.getInt(MANAGING_TOTAL_NUMBER_KEY);
			managingNumber = jsonObj.getInt(MANAGING_NUMBER_KEY);
			
			try {
				longitude = jsonObj.getDouble(LONGITUDE_KEY);
				latitude = jsonObj.getDouble(LATITUDE_KEY);
			}
			catch (Exception e) {
				longitude = null;
				latitude = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
