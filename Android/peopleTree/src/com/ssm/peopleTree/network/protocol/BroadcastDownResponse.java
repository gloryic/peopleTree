package com.ssm.peopleTree.network.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class BroadcastDownResponse extends Response {
	// 브로드 캐스트 공지 메세지 전송 프로토콜 결과
	
	public ArrayList<Integer> childen;
		
	public BroadcastDownResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnInit() {
		childen = new ArrayList<Integer>();
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			JSONArray jsonArr = jsonObj.getJSONArray(CHILDREN_KEY);
			for (int i = 0; !jsonArr.isNull(i); i++) {
				childen.add(jsonArr.getInt(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			childen.clear();
		}
	}
}
