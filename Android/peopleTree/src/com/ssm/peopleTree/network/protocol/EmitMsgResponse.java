package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class EmitMsgResponse extends Response {
	// 브로드 캐스트 공지 메세지 전송 프로토콜 결과
		
	public int sendCount;
	
	public EmitMsgResponse(JSONObject jsonObj) {
		super(jsonObj);
	}

	@Override
	void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			sendCount = jsonObj.getInt(SEND_COUNT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
