package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class LoginResponse extends Response {

	public int userNumber;
	
	public LoginResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			userNumber = jsonObj.getInt(USER_NUMBER_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
