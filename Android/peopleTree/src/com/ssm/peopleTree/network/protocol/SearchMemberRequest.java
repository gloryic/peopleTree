package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.ssm.peopleTree.network.NetworkManager;

public class SearchMemberRequest extends Request {

	private static final String REST_PROTOCOL = "/ptree/util/searchMember";
	
	public String keyword;
	public String password;
	
	public SearchMemberRequest(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public JSONObject toJSonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put(KEYWORD_KEY, keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
		
	@Override
	public String toURI() {
		String result = REST_PROTOCOL;
		result += "?" + KEYWORD_KEY + "=" + NetworkManager.getEncodedStr(keyword);
		return result;
	}
	
	@Override
	public int getMethod() {
		return Method.GET;
	}

}
