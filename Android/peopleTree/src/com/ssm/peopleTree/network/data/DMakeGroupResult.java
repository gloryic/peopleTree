package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DMakeGroupResult extends Data {
	// 그룹 만들기  프로토콜 파라미터

	public int ownPhoneNumber;	
	
	public DMakeGroupResult(int ownPhoneNumber) {
		this.ownPhoneNumber = ownPhoneNumber;
	}
	
	public DMakeGroupResult(JSONObject jsonObject) {
		try {
			ownPhoneNumber = jsonObject.getInt(OWN_PHONE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(OWN_PHONE_NAME, ownPhoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + OWN_PHONE_NAME + "=" + Integer.toString(ownPhoneNumber);
		return result;
	}
}
