package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class MakeEdgeResponse extends Response {
	// 연결 신청 프로토콜 응답
	
		public int from;
		public int to;
		public int statusCode;
		public String message;
		
		public MakeEdgeResponse(JSONObject jsonObj) {
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
