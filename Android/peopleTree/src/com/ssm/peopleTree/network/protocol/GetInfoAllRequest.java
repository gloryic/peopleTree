package com.ssm.peopleTree.network.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.data.MemberData;

public class GetInfoAllRequest extends Request {
	// ����� ���� �������� �������� �Ķ����
	
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
