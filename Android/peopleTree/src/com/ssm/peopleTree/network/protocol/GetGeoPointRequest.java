package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class GetGeoPointRequest extends Request {
	// 지역 설정 프로토콜 파라미터
	private static final String REST_PROTOCOL = "/ptree/geoutil/getGeoPoint";

	public int groupMemberId;
	
	public GetGeoPointRequest(int groupMemberId) {
		this.groupMemberId = groupMemberId;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put(GROUP_MEMBER_ID_KEY, groupMemberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObj;
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
