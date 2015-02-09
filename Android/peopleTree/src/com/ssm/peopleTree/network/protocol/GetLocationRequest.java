package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetLocationRequest extends Request {
	// �� ��ġ ���� �������� �������� ��û
	
	private static final String REST_PROTOCOL = "/ptree/location/getlocation"; 
	
	private int groupMemberId;
	
	public GetLocationRequest(int groupMemberId) {
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
		result += "?" + GROUP_MEMBER_ID_KEY + "=" + groupMemberId;
		return result;
	}

	@Override
	public int getMethod() {
		return Method.POST;
	}

}
