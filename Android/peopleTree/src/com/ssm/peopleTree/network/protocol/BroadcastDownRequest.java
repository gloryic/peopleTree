package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class BroadcastDownRequest extends Request {
	// ��ε� ĳ��Ʈ �޼��� ���� ��������
	
	private static final String REST_PROTOCOL = "/ptree/broadcast/down";
	
	public int groupMemberId;
	public int depth;
	public String message;
	
	public BroadcastDownRequest(int groupMemberId, int depth, String message) {
		this.groupMemberId = groupMemberId;
		this.depth = depth;
		this.message = message;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(GROUP_MEMBER_ID_KEY, groupMemberId);
			json.put(DEPTH_KEY, depth);
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
		result += "&" + DEPTH_KEY + "=" + Integer.toString(depth);
		result += "&" + MESSAGE_KEY + "=" + (message);
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}

}
