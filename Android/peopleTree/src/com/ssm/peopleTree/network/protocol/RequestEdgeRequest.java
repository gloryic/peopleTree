package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;

public class RequestEdgeRequest extends Request {
	// ���� ��û �������� ��û

	private static final String REST_PROTOCOL = "/ptree/event/request/edge"; 
	
	public int from;
	public int to;
	public int statusCode;
	
	public RequestEdgeRequest(int from, int to, int statusCode) {
		this.from = from;
		this.to = to;
		this.statusCode = statusCode;
	}
	
	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(FROM_KEY, from);
			json.put(TO_KEY, to);
			json.put(STATUS_CODE_KEY, statusCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + FROM_KEY + "=" + Integer.toString(from);
		result += "&" + TO_KEY + "=" + Integer.toString(to);
		result += "&" + STATUS_CODE_KEY + "=" + Integer.toString(statusCode);
		
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.POST;
	}

}
