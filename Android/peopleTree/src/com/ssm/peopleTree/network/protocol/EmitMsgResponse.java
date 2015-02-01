package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class EmitMsgResponse extends Protocol {
	// ��ε� ĳ��Ʈ ���� �޼��� ���� �������� ���
		
	public int status;
	public int sendCount;
	
	public EmitMsgResponse(int status, int sendCount) {
		this.status = status;
		this.sendCount = sendCount;
	}
	
	public EmitMsgResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
			sendCount = jsonObject.getInt(SEND_COUNT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_KEY, status);
			json.put(SEND_COUNT_KEY, sendCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_KEY + "=" + Integer.toString(status);
		result += "&" + SEND_COUNT_KEY + "=" + Integer.toString(sendCount);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
