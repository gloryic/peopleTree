package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.network.NetworkManager;

public class BroadcastMsgRequest extends Request {
	// 브로드 캐스트 메세지 전송 프로토콜
	
	private static final String REST_PROTOCOL = "/ptree/broadcast/message";
	
	public int from;
	public int to;
	public int statusCode;
	public String message;
	
	public BroadcastMsgRequest(int from, int to, int statusCode,String message) {
		this.from = from;
		this.to = to;
		this.statusCode = statusCode;
		this.message = message;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(FROM_KEY, from);
			json.put(TO_KEY, to);
			json.put(STATUS_CODE_KEY, statusCode);
			json.put(MESSAGE_KEY, message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + FROM_KEY + "=" + Integer.toString(from);
		result += "&" + TO_KEY + "=" + Integer.toString(to);
		result += "&" + STATUS_CODE_KEY + "=" + Integer.toString(statusCode);
		result += "&" + MESSAGE_KEY + "=" + NetworkManager.getEncodedStr(message);
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}

}
