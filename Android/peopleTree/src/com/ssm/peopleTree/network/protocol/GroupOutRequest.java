package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GroupOutRequest extends Request {
	// 브로드 캐스트 메세지 전송 프로토콜
	
	private static final String REST_PROTOCOL = "/ptree/group/out";
	
	public int groupMemberId;

	public GroupOutRequest(int groupMemberId) {
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
