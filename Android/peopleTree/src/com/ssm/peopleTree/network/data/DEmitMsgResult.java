package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DEmitMsgResult extends Data {
	// ��ε� ĳ��Ʈ ���� �޼��� ���� �������� ���
		
	public int status;
	public int sendCount;
	
	public DEmitMsgResult(int status, int sendCount) {
		this.status = status;
		this.sendCount = sendCount;
	}
	
	public DEmitMsgResult(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_NAME);
			sendCount = jsonObject.getInt(SEND_COUNT_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_NAME, status);
			json.put(SEND_COUNT_NAME, sendCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_NAME + "=" + Integer.toString(status);
		result += "&?" + SEND_COUNT_NAME + "=" + Integer.toString(sendCount);
		return result;
	}
}
