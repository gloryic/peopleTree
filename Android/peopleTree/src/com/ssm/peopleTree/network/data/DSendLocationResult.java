package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DSendLocationResult extends Data {
	// 위치 정보 전송 프로토콜 결과

	public int status;
	public int validUserNumber;
	public int edgeStatus;
	public int parentId;
	public int groupCode;
	
	public DSendLocationResult(int status, int validUserNumber, int edgeStatus, int parentId, int groupCode) {
		this.status = status;
		this.validUserNumber = validUserNumber;
		this.edgeStatus = edgeStatus;
		this. parentId = parentId;
		this.groupCode = groupCode;
	}
	
	public DSendLocationResult(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_NAME);
			validUserNumber = jsonObject.getInt(VALID_USER_NUM_NAME);
			edgeStatus = jsonObject.getInt(EDGE_STATUS_NAME);
			parentId = jsonObject.getInt(PARENT_ID_NAME);
			groupCode = jsonObject.getInt(GROUP_CODE_NAME);
			
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
			json.put(EDGE_STATUS_NAME, edgeStatus);
			json.put(PARENT_ID_NAME, parentId);
			json.put(GROUP_CODE_NAME, groupCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_NAME + "=" + Integer.toString(status);
		result += "&?" + VALID_USER_NUM_NAME + "=" + Integer.toString(validUserNumber);
		result += "&?" + EDGE_STATUS_NAME + "=" + Integer.toString(edgeStatus);
		result += "&?" + PARENT_ID_NAME + "=" + Integer.toString(parentId);
		result += "&?" + GROUP_CODE_NAME + "=" + Integer.toString(groupCode);
		return result;
	}
}
