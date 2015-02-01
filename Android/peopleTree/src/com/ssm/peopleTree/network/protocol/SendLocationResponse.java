package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class SendLocationResponse extends Protocol {
	// 위치 정보 전송 프로토콜 결과

	public int status;
	public int validUserNumber;
	public int edgeStatus;
	public int parentId;
	public int groupCode;
	
	public SendLocationResponse(int status, int validUserNumber, int edgeStatus, int parentId, int groupCode) {
		this.status = status;
		this.validUserNumber = validUserNumber;
		this.edgeStatus = edgeStatus;
		this. parentId = parentId;
		this.groupCode = groupCode;
	}
	
	public SendLocationResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			validUserNumber = jsonObject.getInt(VALID_USER_NUM_KEY);
			edgeStatus = jsonObject.getInt(EDGE_STATUS_KEY);
			parentId = jsonObject.getInt(PARENT_ID_KEY);
			groupCode = jsonObject.getInt(GROUP_CODE_KEY);
			
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
			json.put(EDGE_STATUS_KEY, edgeStatus);
			json.put(PARENT_ID_KEY, parentId);
			json.put(GROUP_CODE_KEY, groupCode);
			
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
		result += "&" + EDGE_STATUS_KEY + "=" + Integer.toString(edgeStatus);
		result += "&" + PARENT_ID_KEY + "=" + Integer.toString(parentId);
		result += "&" + GROUP_CODE_KEY + "=" + Integer.toString(groupCode);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
