package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class EmitMsgRequest extends Protocol {
	// 브로드 캐스트 공지 메세지 전송 프로토콜
	
	public int userNumber;
	public String message;
	
	public EmitMsgRequest(int userId, String message) {
		this.userNumber = userId;
		this.message = message;
	}
	
	public EmitMsgRequest(JSONObject jsonObject) {
		try {
			userNumber = jsonObject.getInt(USER_NUM_KEY);
			message = jsonObject.getString(MESSAGE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_NUM_KEY, userNumber);
			json.put(MESSAGE_KEY, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_NUM_KEY + "=" + Integer.toString(userNumber);
		result += "&" + MESSAGE_KEY + "=" + message;
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
