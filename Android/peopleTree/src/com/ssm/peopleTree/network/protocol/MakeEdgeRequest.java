package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class MakeEdgeRequest extends Request {
	// 연결 수락 프로토콜 요청
	
	private static final String REST_PROTOCOL = "/ptree/make/edge"; 
	
	private int groupMemberId;
	
	public MakeEdgeRequest(int groupMemberId) {
		this.groupMemberId = groupMemberId;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(GROUP_MEMBER_ID_KEY, groupMemberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + GROUP_MEMBER_ID_KEY + "=" + Integer.toString(groupMemberId);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
}
