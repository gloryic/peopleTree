package com.ssm.peopleTree.network.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.data.MemberData;

public class GetInfoAllRequest extends Request {
	// 사용자 정보 가져오기 프로토콜 파라미터
	
	private static final String REST_PROTOCOL = "/ptree/getinfoall/group/member"; 
	
	private int groupMemberId;
	
	public GetInfoAllRequest(int groupMemberId) {
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
		return Method.GET;
	}
	
}
