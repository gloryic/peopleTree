package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DSetRangeRelResult extends Data {
	// 지역 설정(상대) 프로토콜 결과

	public int status;
	public int validUserNumber;
	public int edgeType;
		
	public DSetRangeRelResult(int status, int validUserNumber, int edgeType) {
		this.status = status;
		this.validUserNumber = validUserNumber;
		this.edgeType = edgeType;
	}
	
	public DSetRangeRelResult(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_NAME);
			validUserNumber = jsonObject.getInt(VALID_USER_NUM_NAME);
			edgeType = jsonObject.getInt(EDGE_TYPE_NAME);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_NAME, status);
			json.put(VALID_USER_NUM_NAME, validUserNumber);
			json.put(EDGE_TYPE_NAME, edgeType);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_ID_NAME + "=" + Integer.toString(status);
		result += "&?" + GROUP_ID_NAME + "=" + Integer.toString(validUserNumber);
		result += "&?" + EDGE_TYPE_NAME + "=" + Integer.toString(edgeType);
		return result;
	}
}
