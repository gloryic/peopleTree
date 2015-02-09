package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class BroadcastUpRequest extends Request {
	// 브로드 캐스트 메세지 전송 프로토콜 요청
	
	private static final String REST_PROTOCOL = "/ptree/broadcast/up";
	
	public int groupMemberId;
	public int accumulateWarning;
	public String message;
	
	public BroadcastUpRequest(int groupMemberId, int accumulateWarning, String message) {
		this.groupMemberId = groupMemberId;
		this.accumulateWarning = accumulateWarning;
		this.message = message;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(GROUP_MEMBER_ID_KEY, groupMemberId);
			json.put(ACCUMULATE_WARNING_KEY, accumulateWarning);
			json.put(MESSAGE_KEY, message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + GROUP_MEMBER_ID_KEY + "=" + Integer.toString(groupMemberId);
		result += "&" + ACCUMULATE_WARNING_KEY + "=" + Integer.toString(accumulateWarning);
		result += "&" + MESSAGE_KEY + "=" + (message);
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}

}
