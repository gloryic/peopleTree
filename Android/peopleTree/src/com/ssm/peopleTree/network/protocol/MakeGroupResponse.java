package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class MakeGroupResponse extends Protocol {
	// 그룹 만들기  프로토콜 파라미터

	public int status;
	
	public MakeGroupResponse(JSONObject jsonObject) {
		try {
			status = jsonObject.getInt(STATUS_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public String toString() {
		String result = "";
		result += "?" + STATUS_KEY + "=" + status;
		return result;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(STATUS_KEY, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public int getMethod() {
		return Method.POST;
	}

}
