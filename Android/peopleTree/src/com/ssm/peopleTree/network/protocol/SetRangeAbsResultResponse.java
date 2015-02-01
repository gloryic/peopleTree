package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class SetRangeAbsResultResponse extends Protocol {
	// 지역 설정(절대) 프로토콜 결과

	public int status;
	public int validUserNumber;
	public int edgeType;
	
	public SetRangeAbsResultResponse(int status, int validUserNumber, int edgeType) {
		this.status = status;
		this.validUserNumber = validUserNumber;
		this.edgeType = edgeType;
	}
	
	public SetRangeAbsResultResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			validUserNumber = jsonObject.getInt(VALID_USER_NUM_KEY);
			edgeType = jsonObject.getInt(EDGE_TYPE_KEY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_KEY, status);
			json.put(VALID_USER_NUM_KEY, validUserNumber);
			json.put(EDGE_TYPE_KEY, edgeType);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_KEY + "=" + Integer.toString(status);
		result += "&" + VALID_USER_NUM_KEY + "=" + Integer.toString(validUserNumber);
		result += "&" + EDGE_TYPE_KEY + "=" + Integer.toString(edgeType);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
