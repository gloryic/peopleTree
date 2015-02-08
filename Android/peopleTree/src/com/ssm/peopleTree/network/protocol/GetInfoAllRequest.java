package com.ssm.peopleTree.network.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.data.MemberData;

public class GetInfoAllRequest extends Request {
	// 사용자 정보 가져오기 프로토콜 파라미터
	
	private static final String REST_PROTOCOL = "/ptree/getinfoall/group/member"; 
	
	private int userNumber;
	
	public GetInfoAllRequest(int userNumber) {
		this.userNumber = userNumber;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_NUMBER_KEY, userNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + USER_NUMBER_KEY + "=" + Integer.toString(userNumber);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}
	
}
