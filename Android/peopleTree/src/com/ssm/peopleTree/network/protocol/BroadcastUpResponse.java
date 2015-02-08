package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class BroadcastUpResponse extends Response {
	// 브로드 캐스트 공지 메세지 전송 프로토콜 응답
		
	public ArrayList<Integer> parents;
		
	public BroadcastUpResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnInit() {
		parents = new ArrayList<Integer>();
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			JSONArray jsonArr = jsonObj.getJSONArray(PARENTS_KEY);
			for (int i = 0; !jsonArr.isNull(i); i++) {
				parents.add(jsonArr.getInt(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			parents.clear();
		}
	}
}
