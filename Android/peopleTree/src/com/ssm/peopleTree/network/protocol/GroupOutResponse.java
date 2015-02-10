package com.ssm.peopleTree.network.protocol;

import org.json.JSONObject;

public class GroupOutResponse extends Response {
	// 브로드 캐스트 공지 메세지 전송 프로토콜 결과
		
	public GroupOutResponse(JSONObject jsonObj) {
		super(jsonObj);
	}
}
