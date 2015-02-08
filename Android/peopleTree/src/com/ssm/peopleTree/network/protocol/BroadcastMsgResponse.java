package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class BroadcastMsgResponse extends Response {
	// 브로드 캐스트 공지 메세지 전송 프로토콜 결과
		
	public int from;
	public int to;
	public int statusCode;
	public String message;
	
	public BroadcastMsgResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
	
	@Override
	protected void OnSuccess(Object responseData) {
		try {
			JSONObject jsonObj = (JSONObject)responseData;
			
			from = jsonObj.getInt(FROM_KEY);
			to = jsonObj.getInt(TO_KEY);
			statusCode = jsonObj.getInt(STATUS_CODE_KEY);
			message = jsonObj.getString(MESSAGE_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
