package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetInfoAllRequest extends Request {
	// ����� ���� �������� �������� �Ķ����
	
	private static final String REST_PROTOCOL = "/ptree/getinfoall/group/member"; 
	
	private int myGroupMemberId;
	private int groupMemberId;
	
	public GetInfoAllRequest(int myGroupMemberId, int groupMemberId) {
		this.myGroupMemberId = myGroupMemberId;
		this.groupMemberId = groupMemberId;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(MY_GROUP_MEMBER_ID_KEY, myGroupMemberId);
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
		result += "&" + MY_GROUP_MEMBER_ID_KEY + "=" + Integer.toString(myGroupMemberId);
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.GET;
	}
	
}
