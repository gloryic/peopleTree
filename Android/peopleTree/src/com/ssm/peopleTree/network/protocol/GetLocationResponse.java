package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class GetLocationResponse extends Response {
	// 현 위치 정보 가져오기 프로토콜 응답

	public Double latitude;
	public Double longitude;
	
	public GetLocationResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			longitude = jsonObj.getDouble(LONGITUDE_KEY);
			latitude = jsonObj.getDouble(LATITUDE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			
			longitude = null;
			latitude = null;
		}
	}
}
