package com.ssm.peopleTree.network.data;

import org.json.JSONObject;

public class DEmitMsg extends Data {
	// 브로드 캐스트 공지 메세지 전송 프로토콜
	
	public int userId;
	public String message;
	
	public DEmitMsg(int userId, String message) {
		this.userId = userId;
		this.message = message;
	}
	
	public DEmitMsg(JSONObject jsonObject) {
		try {
			userId = jsonObject.getInt(USER_ID_NAME);
			message = jsonObject.getString(MESSAGE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_ID_NAME, userId);
			json.put(MESSAGE_NAME, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "?" + USER_ID_NAME + "=" + Integer.toString(userId);
		result += "&?" + MESSAGE_NAME + "=" + message;
		return result;
	}
}
